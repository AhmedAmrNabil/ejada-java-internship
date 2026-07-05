package Project2.http.router;

import Project2.http.model.HttpRequest;
import Project2.http.model.HttpResponse;

@FunctionalInterface
public interface Handler {
	HttpResponse handle(HttpRequest request);
}