package io.microservices.ratingsdataservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microservices.ratingsdataservice.dto.UserRatingsResponse;
import io.microservices.ratingsdataservice.entity.Rating;

@RestController
@RequestMapping("/api/v1/ratingsdata")
public class RatingsController {

	@GetMapping("/{movieId}")
	public Rating getRatingByMovieId(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}

	@GetMapping("/user/{userId}")
	public UserRatingsResponse getRatingsByUserId(@PathVariable String userId) {
		return new UserRatingsResponse(userId, "John Doe", List.of(
				new Rating("123", 5),
				new Rating("456", 4),
				new Rating("789", 3)));
	}
}
