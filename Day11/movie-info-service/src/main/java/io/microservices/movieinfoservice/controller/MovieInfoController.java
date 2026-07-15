package io.microservices.movieinfoservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microservices.movieinfoservice.entity.Movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieInfoController {

	@GetMapping("/{movieId}")
	public Movie getMovieById(@PathVariable String movieId) {
		return new Movie(movieId, "Sample Movie");
	}
}
