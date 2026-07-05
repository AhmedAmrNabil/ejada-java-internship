package Project2.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Project2.http.model.HttpRequest;
import Project2.http.model.HttpResponse;
import Project2.http.router.Router;

public class ClientHandler implements Runnable {
	private final Socket client;
	private final Router router;

	public ClientHandler(Socket client, Router router) {
		this.client = client;
		this.router = router;
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

			HttpResponse res = router.route(req);
			out.print(res.toHttpString());
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
