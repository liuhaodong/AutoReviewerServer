package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException{
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port 8000:");
	        Socket s = new Socket(serverAddress, 8000);
	        BufferedReader input =
	            new BufferedReader(new InputStreamReader(s.getInputStream()));
	        String answer = input.readLine();
	        JOptionPane.showMessageDialog(null, answer);
	        System.exit(0);
	}
}
