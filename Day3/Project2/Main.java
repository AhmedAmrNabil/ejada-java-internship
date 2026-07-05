package Project2;

import java.io.IOException;
import java.util.Map;

import Project2.http.model.HttpResponse;
import Project2.http.model.HttpStatus;
import Project2.http.router.Router;
import Project2.http.server.HttpServer;

public class Main {
	public static void main(String[] args) throws IOException {
		Router router = new Router();

		router.get("/", req -> new HttpResponse(HttpStatus.OK, Map.of("Content-Type", "text/plain"), "hello world"));

		router.get("/hello",
				req -> new HttpResponse(HttpStatus.OK, Map.of("Content-Type", "application/json"), "{\"msg\":\"hi\"}"));

		router.post("/echo",
				req -> new HttpResponse(HttpStatus.OK, Map.of("Content-Type", "application/json"), req.body()));

		new HttpServer(8080, router).start();
	}
}
