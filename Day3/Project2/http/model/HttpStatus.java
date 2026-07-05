package Project2.http.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum HttpStatus {

	// 1xx Informational
	CONTINUE(100, "Continue"),
	SWITCHING_PROTOCOLS(101, "Switching Protocols"),

	// 2xx Success
	OK(200, "OK"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NO_CONTENT(204, "No Content"),

	// 3xx Redirection
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	FOUND(302, "Found"),
	NOT_MODIFIED(304, "Not Modified"),
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	PERMANENT_REDIRECT(308, "Permanent Redirect"),

	// 4xx Client Errors
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	CONFLICT(409, "Conflict"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	UNPROCESSABLE_CONTENT(422, "Unprocessable Content"),
	TOO_MANY_REQUESTS(429, "Too Many Requests"),

	// 5xx Server Errors
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NOT_IMPLEMENTED(501, "Not Implemented"),
	BAD_GATEWAY(502, "Bad Gateway"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	GATEWAY_TIMEOUT(504, "Gateway Timeout");

	private final int code;
	private final String reason;

	HttpStatus(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	public int code() {
		return code;
	}

	public String reason() {
		return reason;
	}

	@Override
	public String toString() {
		return code + " " + reason;
	}

	private static final Map<Integer, HttpStatus> BY_CODE = Stream.of(values())
			.collect(Collectors.toUnmodifiableMap(HttpStatus::code, s -> s));

	public static HttpStatus fromCode(int code) {
		return BY_CODE.get(code);
	}
}
