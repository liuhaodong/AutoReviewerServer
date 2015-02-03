package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author haodongl
 * Test Server
 */
public class TestServer {
	
	private static final int PORT = 8000;
	
	/**
	 * @param args some args.
	 * @throws IOException some exceptions.
	 */
	public static void main(final String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		while (true) {
			// System.out.print(serverSocket.getLocalPort());
			Socket clientSocket = serverSocket.accept();

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				out.println(inputLine);

				if (inputLine.equals("Bye."))
					break;
			}
			out.close();
			in.close();
			clientSocket.close();
			serverSocket.close();
		}
	}
}
