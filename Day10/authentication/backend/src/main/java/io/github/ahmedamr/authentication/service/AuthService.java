package io.github.ahmedamr.authentication.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.ahmedamr.authentication.dto.auth.AuthResponse;
import io.github.ahmedamr.authentication.dto.auth.LoginRequest;
import io.github.ahmedamr.authentication.dto.auth.RegisterRequest;
import io.github.ahmedamr.authentication.entity.AppUser;
import io.github.ahmedamr.authentication.entity.RefreshTokenInfo;
import io.github.ahmedamr.authentication.entity.Role;
import io.github.ahmedamr.authentication.entity.SecurityUser;
import io.github.ahmedamr.authentication.exception.DuplicateUsernameException;
import io.github.ahmedamr.authentication.exception.ResourceNotFoundException;
import io.github.ahmedamr.authentication.exception.RevokedRefreshTokenException;
import io.github.ahmedamr.authentication.repository.RoleRepository;
import io.github.ahmedamr.authentication.repository.UserRepository;
import io.github.ahmedamr.authentication.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;

	@Transactional
	public AuthResponse login(LoginRequest request) {
		var authToken = new UsernamePasswordAuthenticationToken(request.username(),
				request.password());
		Authentication authentication = authenticationManager.authenticate(authToken);

		SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
		AppUser user = securityUser.getUser();

		LocalDateTime currentDate = LocalDateTime.now();
		String newUuid = UUID.randomUUID().toString();
		refreshTokenService.issue(user, newUuid, currentDate);

		String accessToken = jwtUtils.generateAccessToken(user.getId().toString(), currentDate);
		String refreshToken = jwtUtils.generateRefreshToken(user.getId().toString(), currentDate, newUuid);

		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional
	public AuthResponse register(RegisterRequest request) {
		String username = request.username();
		if (userRepository.existsByUsername(username)) {
			throw new DuplicateUsernameException();
		}

		Role defaultRole = roleRepository.findByName("NormalUser")
				.orElseThrow(() -> new ResourceNotFoundException("{error.role.not-found}", "NormalUser"));

		AppUser user = AppUser.builder()
				.username(username)
				.password(passwordEncoder.encode(request.password()))
				.build();
		user.getRoles().add(defaultRole);

		try {
			AppUser savedUser = userRepository.saveAndFlush(user);
			LocalDateTime currentDate = LocalDateTime.now();
			String newUuid = UUID.randomUUID().toString();
			refreshTokenService.issue(savedUser, newUuid, currentDate);
			String accessToken = jwtUtils.generateAccessToken(savedUser.getId().toString(), currentDate);
			String refreshToken = jwtUtils.generateRefreshToken(savedUser.getId().toString(), currentDate, newUuid);
			return new AuthResponse(accessToken, refreshToken);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateUsernameException();
		}
	}

	@Transactional
	public String logout(AppUser user, String refreshToken) {
		// Revoke the specific refresh token for the user
		refreshTokenService.logoutDevice(user, refreshToken);
		return "Logout successful";
	}

	@Transactional
	public AuthResponse refreshToken(String refreshToken) throws RevokedRefreshTokenException {
		LocalDateTime currentDate = LocalDateTime.now();
		Claims claims = jwtUtils.extractClaims(refreshToken, true);

		String presentedJti = claims.getId();

		String newJti = UUID.randomUUID().toString();
		RefreshTokenInfo rotated = refreshTokenService.validateAndRotate(presentedJti, newJti, currentDate);
		AppUser user = rotated.getUser();

		String newAccessToken = jwtUtils.generateAccessToken(user.getId().toString(), currentDate);
		String newRefreshToken = jwtUtils.generateRefreshToken(user.getId().toString(), currentDate, newJti);

		return new AuthResponse(newAccessToken, newRefreshToken);
	}
}
