package Project2;

import java.io.IOException;

import Project2.http.server.HttpServer;
public class Main {
	public static void main(String[] args) throws IOException {
		new HttpServer(8080).start();
	}
}
