# Day 11 Microservices Project

This folder contains a small Spring Boot microservices system that runs entirely with Docker Compose.

The project has four services:

- `discovery-server` - Eureka discovery service
- `movie-info-service` - provides movie details
- `ratings-data-service` - provides user ratings
- `movie-catalog-service` - main service that combines data from the other services

## How it starts

Run the whole system with:

```bash
docker compose up
```

Compose starts the services in the following order:

1. `discovery-server` starts first.
2. `movie-info-service` and `ratings-data-service` wait for the discovery server and then start.
3. `movie-catalog-service` starts after the discovery server is healthy and the two supporting services have started.

## Ports

- Discovery server: `8761`
- Movie catalog service: `8081`
- Movie info service: `8082`
- Ratings data service: `8083`

The discovery server port is exposed for local testing. In a production setup it should usually stay private behind a firewall or internal network.

## Build details

All services use the shared root `Dockerfile` in this folder. The Docker build receives the service directory through the `SERVICE_DIR` build argument, then builds the selected Spring Boot app from that folder.