package io.github.ahmedamr.authentication.dto.user;

import java.time.LocalDateTime;

public record AppUserResponse(
		String username,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
