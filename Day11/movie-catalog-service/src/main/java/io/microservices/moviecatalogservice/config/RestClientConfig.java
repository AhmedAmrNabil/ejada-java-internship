package io.microservices.moviecatalogservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	RestClient.Builder lbRestClientBuilder() {
		return RestClient.builder();
	}

	@Bean
	@Primary
	RestClient.Builder directRestClientBuilder() {
		return RestClient.builder();
	}

	@Bean
	@Primary
	RestClient serviceRestClient(
			@Qualifier("lbRestClientBuilder") RestClient.Builder builder) {

		return builder.build();
	}

	@Bean
	RestClient directRestClient(
			@Qualifier("directRestClientBuilder") RestClient.Builder builder) {
		return builder.build();
	}

}
