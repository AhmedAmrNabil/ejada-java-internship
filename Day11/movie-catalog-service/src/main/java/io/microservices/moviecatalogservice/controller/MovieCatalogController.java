package io.microservices.moviecatalogservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microservices.moviecatalogservice.entity.CatalogItem;

@RestController
@RequestMapping("/api/v1/catalog")
public class MovieCatalogController {

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(String userId) {
		List<CatalogItem> catalog = List.of(
				new CatalogItem("The Lion King", "Animation Movie", 5),
				new CatalogItem("Transformers", "Action Movie", 4),
				new CatalogItem("The Avengers", "Action Movie", 3));
		return catalog;
	}

}
