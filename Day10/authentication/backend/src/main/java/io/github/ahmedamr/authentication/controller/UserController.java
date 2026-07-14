package io.github.ahmedamr.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ahmedamr.authentication.entity.AppUser;
import io.github.ahmedamr.authentication.entity.SecurityUser;
import io.github.ahmedamr.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
		AppUser user = securityUser.getUser();
		return ResponseEntity.ok(userService.getCurrentUser(user));
	}

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

}
