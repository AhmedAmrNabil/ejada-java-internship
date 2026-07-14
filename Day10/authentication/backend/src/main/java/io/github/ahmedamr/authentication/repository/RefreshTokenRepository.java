package io.github.ahmedamr.authentication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.ahmedamr.authentication.entity.RefreshTokenInfo;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenInfo, String> {
	List<RefreshTokenInfo> findByUserIdAndRevokedAtIsNull(Long userId);

	Optional<RefreshTokenInfo> findByJtiAndUserId(String jti, Long userId);
}
