package edu.cmu.lti.bic.autoreviewer.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * listener for review.
 * @author jhe
 *
 */
public class EEGReviewListener implements Runnable {

	private int port;

	private ServerSocket listener;
	
	public static final Lock _mutex = new ReentrantLock(true);
	

	/**
	 * @throws IOException 
	 */
	public EEGReviewListener() throws IOException {
		this.port = ServerConfiguration.DEFAULT_REVIEW_PORT;
		listener = new ServerSocket(this.port);
	}

	/**
	 * @param pPort Port listening to eeg data.
	 * @throws IOException 
	 */
	public EEGReviewListener(int pPort) throws IOException {
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
			EEGReviewHandler eegHandler = new EEGReviewHandler(clientSocket);
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
