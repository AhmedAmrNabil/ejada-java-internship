package Project2.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Project2.http.router.Router;

public class HttpServer {
	private final int port;
	private final Router router;

	public HttpServer(int port, Router router) {
		this.port = port;
		this.router = router;
	}

	public void start() throws IOException {
		try (ServerSocket ss = new ServerSocket(port)) {
			System.out.println("Listening on port " + port);
			while (true) {
				Socket client = ss.accept();
				new Thread(new ClientHandler(client, router)).start();
			}
		}
	}
}