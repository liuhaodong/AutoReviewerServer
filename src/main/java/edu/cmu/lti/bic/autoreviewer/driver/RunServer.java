package edu.cmu.lti.bic.autoreviewer.driver;

import java.io.IOException;

import edu.cmu.lti.bic.autoreviewer.io.EEGDataLisenter;

public class RunServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		EEGDataLisenter eegDataLisenter = new EEGDataLisenter();
		new Thread(eegDataLisenter).start();
		
	}

}
