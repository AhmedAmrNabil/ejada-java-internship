package io.microservices.movieinfoservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microservices.movieinfoservice.entity.Movie;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieInfoController {

	Map<String, Movie> movieMap = Map.of(
			"123", new Movie("123", "The Lion King", "Animation Movie"),
			"456", new Movie("456", "Transformers", "Action Movie"),
			"789", new Movie("789", "The Avengers", "Action Movie"));

	@GetMapping("/{movieId}")
	public Movie getMovieById(@PathVariable String movieId) {
		return Optional.ofNullable(movieMap.get(movieId))
				.orElse(new Movie("0", "Movie not found", "No description available"));
	}
}
