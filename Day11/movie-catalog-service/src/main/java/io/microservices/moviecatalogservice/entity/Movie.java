package io.microservices.moviecatalogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Movie {
	private String movieId;
	private String name;
	private String description;
}

