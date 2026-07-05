package Project2.http.model;

import java.util.Map;

public record HttpResponse(HttpStatus status, Map<String, String> headers, String body) {
	public String toHttpString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ").append(status).append("\r\n");

		headers.forEach((k, v) -> sb.append(k).append(": ").append(v).append("\r\n"));

		byte[] bodyBytes = body.getBytes();
		sb.append("Content-Length: ").append(bodyBytes.length).append("\r\n");
		sb.append("Connection: close\r\n");
		sb.append("\r\n");
		sb.append(body);

		return sb.toString();
	}
}