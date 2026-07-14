package io.github.ahmedamr.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
		String accessTokenSecret,
		String refreshTokenSecret,
		long accessTokenExpiration,
		long refreshTokenExpiration) {
}