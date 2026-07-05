package Project2.http.router;

import Project2.http.model.HttpRequest;
import Project2.http.model.HttpResponse;
import Project2.http.model.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class Router {
	private final Map<String, Map<String, Handler>> routes = new HashMap<>();

	public Router get(String path, Handler handler) {
		return register("GET", path, handler);
	}

	public Router post(String path, Handler handler) {
		return register("POST", path, handler);
	}

	public Router put(String path, Handler handler) {
		return register("PUT", path, handler);
	}

	public Router delete(String path, Handler handler) {
		return register("DELETE", path, handler);
	}

	private Router register(String method, String path, Handler handler) {
		routes.computeIfAbsent(method, m -> new HashMap<>()).put(path, handler);
		return this; // chaining
	}

	public HttpResponse route(HttpRequest request) {
		Map<String, Handler> methodRoutes = routes.get(request.method());

		if (methodRoutes == null) {
			return notFound();
		}

		Handler handler = methodRoutes.get(request.path());

		if (handler == null) {
			return notFound();
		}

		return handler.handle(request);
	}

	private HttpResponse notFound() {
		return new HttpResponse(HttpStatus.NOT_FOUND, Map.of(), "{\"error\":\"Not Found\"}");
	}
}
