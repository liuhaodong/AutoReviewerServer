package edu.cmu.lti.bic.autoreviewer.driver;

import java.io.IOException;

import edu.cmu.lti.bic.autoreviewer.io.EEGDataLisenter;

/**
 * @author haodongl
 * Main method to run the server.
 */
public class RunServer {

	/**
	 * @param args Command line args.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		EEGDataLisenter eegDataLisenter = new EEGDataLisenter();
		new Thread(eegDataLisenter).start();
		
	}

}
