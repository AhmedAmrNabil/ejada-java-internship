package io.microservices.ratingsdataservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microservices.ratingsdataservice.entity.Rating;

@RestController
@RequestMapping("/api/v1/ratingsdata")
public class RatingsController {
	
	@GetMapping("/{movieId}")
	public Rating getRatingByMovieId(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}
}
