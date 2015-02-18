package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.db.DBManager;
import edu.cmu.lti.bic.autoreviewer.ds.Arguments;
import edu.cmu.lti.bic.autoreviewer.function.Classifier;
import edu.cmu.lti.bic.autoreviewer.function.Reviewer;
import edu.cmu.lti.bic.autoreviewer.function.TimelineManager;

/***
 * core function to handle review request
 * @author jhe
 *
 */
public class EEGReviewHandler implements Runnable {

	private Socket socket;

	private DBManager dbManager;

	private TimelineManager tmManager;

	private Reviewer mReviewer;

	private Classifier mclassifier;

	/**
	 * @param pSocket
	 *            socket connecting client.
	 */
	public EEGReviewHandler(final Socket pSocket) {
		this.socket = pSocket;
		dbManager = new DBManager();
		tmManager = new TimelineManager();
		mReviewer = new Reviewer();
		mclassifier = new Classifier();
	}

	@Override
	public final void run() {
		try {
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));

			String line;
			Arguments argu = new Arguments();
			while ((line = inStream.readLine()) != null) {
				this.parseArgu(line, argu);
			}

			mclassifier.setClassifierArgument(argu);

			// here  comment
			OutputStream out = this.socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dos.writeUTF("Transformers|testUser|"
					+ df.format(new Date())
					+ "|8.5|TRANSFORMERS: AGE OF EXTINCTION begins after an "
					+ "epic battle left a great city torn, but with the "
					+ "world saved. As humanity picks up the pieces, a"
					+ " shadowy group reveals itself in an attempt to"
					+ " control the direction of history...while an "
					+ "ancient, powerful new menace sets Earth in its"
					+ " crosshairs. With help from a new cast of humans"
					+ " (led by Mark Wahlberg), Optimus Prime and the"
					+ " Autobots rise to meet their most fearsome "
					+ "challenge yet. In an incredible adventure, "
					+ "they are swept up in a war of good and evil,"
					+ " ultimately leading to a climactic battle "
					+ "across the world. (C) Paramount");
			
			// here stop
			
			inStream.close();
			out.close();
			this.socket.close();
			dbManager.closeDB();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***
	 * parse arguments from socket line. arguments should be in
	 * "start: 2015-2-7..." "end: 2015-3-6.." "movie: gozzila" format
	 * 
	 * @param line
	 *            parsed line
	 * @param arg
	 *            argument ds
	 */
	public void parseArgu(String line, Arguments arg) {
		// TODO

	}
}
