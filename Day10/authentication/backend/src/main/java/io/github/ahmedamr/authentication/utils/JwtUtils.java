package io.github.ahmedamr.authentication.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.github.ahmedamr.authentication.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private final JwtProperties jwtProperties;
	private final SecretKey accessTokenSecretKey;
	private final SecretKey refreshTokenSecretKey;

	public JwtUtils(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.accessTokenSecretKey = Keys.hmacShaKeyFor(jwtProperties.accessTokenSecret().getBytes());
		this.refreshTokenSecretKey = Keys.hmacShaKeyFor(jwtProperties.refreshTokenSecret().getBytes());
	}

	public String generateAccessToken(String username, LocalDateTime issuedAt) {
		return buildToken(username, UUID.randomUUID().toString(),
				jwtProperties.accessTokenExpiration(), issuedAt, accessTokenSecretKey);
	}

	public String generateRefreshToken(String username, LocalDateTime issuedAt, String jti) {
		return buildToken(username, jti,
				jwtProperties.refreshTokenExpiration(), issuedAt, refreshTokenSecretKey);
	}

	private String buildToken(String username, String jti, long expirationMillis, LocalDateTime issuedAt, SecretKey key) {
		Date issueDate = Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant());
		return Jwts.builder()
				.id(jti)
				.issuedAt(issueDate)
				.issuer("AuthService")
				.subject(username)
				.expiration(new Date(issueDate.getTime() + expirationMillis))
				.signWith(key)
				.compact();
	}

	public Claims extractClaims(String token, boolean isRefreshToken) {
		SecretKey key = isRefreshToken ? refreshTokenSecretKey : accessTokenSecretKey;
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}