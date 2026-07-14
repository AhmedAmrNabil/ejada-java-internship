package io.github.ahmedamr.authentication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.ahmedamr.authentication.dto.user.AppUserResponse;
import io.github.ahmedamr.authentication.entity.AppUser;
import io.github.ahmedamr.authentication.exception.ResourceNotFoundException;
import io.github.ahmedamr.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public AppUserResponse getCurrentUser(AppUser user) {
		return new AppUserResponse(user.getUsername(), user.getCreatedAt(), user.getUpdatedAt());
	}

	public AppUserResponse findById(Long id) {
		// Implementation for getting user by ID
		return userRepository.findById(id)
				.map(user -> new AppUserResponse(user.getUsername(), user.getCreatedAt(), user.getUpdatedAt()))
				.orElseThrow(() -> new ResourceNotFoundException("{error.user-not-found}"));
	}

	public AppUserResponse findByUsername(String username) {
		// Implementation for getting user by username
		return userRepository.findByUsername(username)
				.map(user -> new AppUserResponse(user.getUsername(), user.getCreatedAt(), user.getUpdatedAt()))
				.orElseThrow(() -> new ResourceNotFoundException("{error.user-not-found}"));
	}

	public List<AppUserResponse> findAllUsers() {
		// Implementation for getting all users
		return userRepository.findAll().stream()
				.map(user -> new AppUserResponse(user.getUsername(), user.getCreatedAt(), user.getUpdatedAt()))
				.toList();
	}
}
