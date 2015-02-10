package edu.cmu.lti.bic.autoreviewer.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;

public class EEGDataLisenter implements Runnable {

	private int port;

	ServerSocket listener;

	public EEGDataLisenter() throws IOException {
		this.port = ServerConfiguration.DEFAULT_PORT;
		listener = new ServerSocket(this.port);
	}

	public EEGDataLisenter(int pPort) throws IOException {
		this.port = pPort;
		listener = new ServerSocket(this.port);
	}

	public void startListen() throws IOException {
		while (true) {
			Socket clientSocket = this.listener.accept();
			EEGDataHandler eegHandler = new EEGDataHandler(clientSocket);
			new Thread(eegHandler).start();
		}
	}

	@Override
	public void run() {
		try {
			this.startListen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
