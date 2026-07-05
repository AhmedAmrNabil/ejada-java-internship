package Project2.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	private final int port;

	public HttpServer(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		try (ServerSocket ss = new ServerSocket(port)) {
			System.out.println("Listening on port " + port);
			while (true) {
				Socket client = ss.accept();
				new Thread(new ClientHandler(client)).start();
			}
		}
	}
}