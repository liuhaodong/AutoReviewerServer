package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * @author haodongl
 * Test Client.
 */
public class TestClient {

	private static final int PORT = 8000;

	/**
	 * @param args some args.
	 * @throws UnknownHostException some exceptions.
	 * @throws IOException some exceptions.
	 */
	public static void main(final String[] args) throws UnknownHostException,
			IOException {
		String serverAddress = JOptionPane
				.showInputDialog("Enter IP Address of a machine that is\n"
						+ "running the date service on port 8000:");
		Socket s = new Socket(serverAddress, PORT);
		BufferedReader input = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		String answer = input.readLine();
		JOptionPane.showMessageDialog(null, answer);
		System.exit(0);
	}
}
