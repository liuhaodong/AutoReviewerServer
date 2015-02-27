package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
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

	private Classifier mClassifier;

	/**
	 * @param pSocket
	 *            socket connecting client.
	 */
	public EEGReviewHandler(final Socket pSocket) {
		this.socket = pSocket;
		dbManager = new DBManager();
		tmManager = new TimelineManager();
		mReviewer = new Reviewer();
		mClassifier = new Classifier();
	}

	@Override
	public final void run() {
		try {
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));

			String line;
			Arguments argu = new Arguments();
			if ((line = inStream.readLine()) != null) {
				this.parseArgu(line, argu);
			}

			mClassifier.setClassifierArgument(argu);

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Input Format Wrong For Parsing");
			e.printStackTrace();
		}
	}

	/***
	 * parse arguments from socket line. arguments should be in
	 * "start: 2015-02-20 13:48:14#end: 2015-02-20 13:48:14#movie: gozzila" format
	 * 
	 * @param line
	 *            parsed line
	 * @param arg
	 *            argument ds
	 * @throws ParseException throw exception when row is not in assumed type
	 */
	public void parseArgu(String line, Arguments arg) throws ParseException {
		// TODO
		String[] args = line.split("#");
		Date startTime = null;
		Date endTime = null;
		String movieName = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (String str: args) {
			if (str.contains("start:")) {
				String time = str.replaceAll("start:", "").trim();
				startTime = df.parse(time);
			} else if (str.contains("end:")) {
				String time = str.replaceAll("end:", "").trim();
				endTime = df.parse(time);
			} else if (str.contains("movie:")) {
				movieName = str.replaceAll("movie:", "").trim();
			}
		}
		if (startTime != null && endTime != null && movieName != null) {
			arg.setArguments(startTime, endTime, movieName);
		}
	}
}
