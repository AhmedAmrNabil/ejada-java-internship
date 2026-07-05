package Project2.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import Project2.http.model.HttpRequest;
import Project2.http.model.HttpResponse;
import Project2.http.model.HttpStatus;

public class ClientHandler implements Runnable {
	private final Socket client;

	public ClientHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try (Socket socket = client;
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

			HttpRequest req = HttpRequest.parse(in);
			if (req == null)
				return;

			String responseBody = "{\"status\":\"ok\"}";
			var resHeader = new HashMap<String, String>();
			resHeader.put("Connection", "Close");
			HttpResponse res = new HttpResponse(HttpStatus.OK, resHeader, responseBody);
			out.print(res.toHttpString());
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
