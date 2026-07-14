package io.github.ahmedamr.authentication.controller;

import io.github.ahmedamr.authentication.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ahmedamr.authentication.dto.auth.AuthResponse;
import io.github.ahmedamr.authentication.dto.auth.LoginRequest;
import io.github.ahmedamr.authentication.dto.auth.RefreshTokenRequest;
import io.github.ahmedamr.authentication.dto.auth.RegisterRequest;
import io.github.ahmedamr.authentication.entity.SecurityUser;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/logout")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> logout(@AuthenticationPrincipal SecurityUser securityUser,
			@RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authService.logout(securityUser.getUser(), request.refreshToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws Exception {
		return ResponseEntity.ok(authService.refreshToken(request.refreshToken()));
	}
}
