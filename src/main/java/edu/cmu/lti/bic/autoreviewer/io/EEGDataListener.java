package edu.cmu.lti.bic.autoreviewer.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;

/**
 * @author haodongl
 * 
 */
public class EEGDataLisenter implements Runnable {

	private int port;

	private ServerSocket listener;
	

	/**
	 * @throws IOException 
	 */
	public EEGDataLisenter() throws IOException {
		this.port = ServerConfiguration.DEFAULT_PORT;
		listener = new ServerSocket(this.port);
	}

	/**
	 * @param pPort Port listening to eeg data.
	 * @throws IOException 
	 */
	public EEGDataLisenter(int pPort) throws IOException {
		this.port = pPort;
		listener = new ServerSocket(this.port);
	}

	/**
	 * This method creates a new thread and runs the listener.
	 * @throws IOException 
	 */
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
