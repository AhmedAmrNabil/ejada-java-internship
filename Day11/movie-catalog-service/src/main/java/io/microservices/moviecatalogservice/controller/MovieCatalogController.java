package io.microservices.moviecatalogservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import io.microservices.moviecatalogservice.dto.UserRatingsResponse;
import io.microservices.moviecatalogservice.entity.CatalogItem;
import io.microservices.moviecatalogservice.entity.Movie;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class MovieCatalogController {

	private final RestClient restClient;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		UserRatingsResponse ratings = restClient.get()
				.uri("http://ratings-data-service/api/v1/ratingsdata/user/" + userId)
				.retrieve()
				.body(UserRatingsResponse.class);

		return ratings.ratings().stream().map(rating -> {
			Movie movie = restClient.get()
					.uri("http://movie-info-service/api/v1/movies/" + rating.getMovieId())
					.retrieve()
					.body(Movie.class);
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).toList();
	}
}
