package io.github.ahmedamr.authentication.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import io.github.ahmedamr.authentication.config.JwtProperties;
import io.github.ahmedamr.authentication.entity.AppUser;
import io.github.ahmedamr.authentication.entity.RefreshTokenInfo;
import io.github.ahmedamr.authentication.exception.RevokedRefreshTokenException;
import io.github.ahmedamr.authentication.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtProperties jwtProperties;

	@Transactional
	public RefreshTokenInfo issue(AppUser user, String jti, LocalDateTime currentDate) {
		RefreshTokenInfo info = RefreshTokenInfo.builder()
				.jti(jti)
				.user(user)
				.issuedAt(currentDate)
				.expiresAt(currentDate.plus(jwtProperties.refreshTokenExpiration(), ChronoUnit.MILLIS))
				.build();
		return refreshTokenRepository.save(info);
	}

	@Transactional
	public RefreshTokenInfo validateAndRotate(String presentedJti, String newJti, LocalDateTime currentDate)
			throws RevokedRefreshTokenException {
		RefreshTokenInfo current = refreshTokenRepository.findById(presentedJti)
				.orElseThrow(() -> new BadCredentialsException("{error.jwt.invalid}"));

		if (current.getRevokedAt() != null) {
			throw new RevokedRefreshTokenException();
		}

		if (current.getExpiresAt().isBefore(currentDate)) {
			throw new ExpiredJwtException(null, null, "{error.jwt.expired}");
		}

		current.setRevokedAt(currentDate);
		current.setReplacedBy(newJti);
		refreshTokenRepository.save(current);

		return issue(current.getUser(), newJti, currentDate);
	}

	@Transactional
	public void revokeAllForUser(AppUser user) {
		List<RefreshTokenInfo> activeTokens = refreshTokenRepository
				.findByUserIdAndRevokedAtIsNull(user.getId());

		LocalDateTime now = LocalDateTime.now();
		activeTokens.forEach(t -> t.setRevokedAt(now));
		refreshTokenRepository.saveAll(activeTokens);
	}

	@Transactional
	public void logoutDevice(AppUser user, String jti) {
		RefreshTokenInfo token = refreshTokenRepository.findByJtiAndUserId(jti, user.getId())
				.orElseThrow(() -> new BadCredentialsException("{error.jwt.invalid}"));
		token.setRevokedAt(LocalDateTime.now());
		refreshTokenRepository.save(token);
	}
}
