package Project2.http.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public record HttpRequest(String method, String path, String version,
		Map<String, String> headers, String body) {

	public static HttpRequest parse(BufferedReader in) throws IOException {
		String requestLine = in.readLine();
		if (requestLine == null)
			return null; // signal "no request" to caller

		String[] parts = requestLine.split(" ");
		String method = parts[0];
		String path = parts[1];
		String version = parts[2];

		var headers = new HashMap<String, String>();
		String line;
		while ((line = in.readLine()) != null && !line.isEmpty()) {
			String[] kv = line.split(": ", 2);
			headers.put(kv[0].trim(), kv[1].trim());
		}

		String body = "";
		if (headers.containsKey("Content-Length")) {
			int len = Integer.parseInt(headers.get("Content-Length"));
			char[] buf = new char[len];
			in.read(buf, 0, len);
			body = new String(buf);
		}

		return new HttpRequest(method, path, version, headers, body);
	}
}
