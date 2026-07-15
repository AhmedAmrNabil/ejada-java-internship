package io.microservices.ratingsdataservice.dto;

import io.microservices.ratingsdataservice.entity.Rating;
import java.util.List;

public record UserRatingsResponse(
		String userId,
		String fullName,
		List<Rating> ratings) {
}
