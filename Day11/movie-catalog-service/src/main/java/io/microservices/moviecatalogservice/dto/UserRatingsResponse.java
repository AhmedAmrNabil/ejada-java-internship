package io.microservices.moviecatalogservice.dto;

import io.microservices.moviecatalogservice.entity.Rating;
import java.util.List;

public record UserRatingsResponse(
		String userId,
		String fullName,
		List<Rating> ratings) {
}
