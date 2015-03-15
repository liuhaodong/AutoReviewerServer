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

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.datastructure.Arguments;
import edu.cmu.lti.bic.autoreviewer.datastructure.ClassifiedData;
import edu.cmu.lti.bic.autoreviewer.datastructure.ReviewResult;
import edu.cmu.lti.bic.autoreviewer.datastructure.Timeline;
import edu.cmu.lti.bic.autoreviewer.db.DBClassifierHelper;
import edu.cmu.lti.bic.autoreviewer.db.DBManager;
import edu.cmu.lti.bic.autoreviewer.function.Classifier;
import edu.cmu.lti.bic.autoreviewer.function.Reviewer;
import edu.cmu.lti.bic.autoreviewer.function.TimelineManager;

/***
 * core function to handle review request
 * 
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
		mClassifier = new Classifier();
	}

	@Override
	public final void run() {
		try {
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));

			String line = inStream.readLine();

			Arguments argu = parseArgu(line);

			DBClassifierHelper tmpHelper = new DBClassifierHelper();

			tmpHelper.dbToCSV(argu);

			// mClassifier.run(argu);

			ClassifiedData classifiedData = new ClassifiedData(
					ServerConfiguration.DEFAULT_CLASSIFIED_DATA_PATH);

			Timeline myTime = tmManager.getTimeline(argu.getMovie());

			ReviewResult myResult = Reviewer.generateReview(myTime,
					classifiedData, argu);

			OutputStream out = this.socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			String reviewResult = myResult.getReview();
			System.out.println(reviewResult);
			dos.writeChars(reviewResult);

//			 SimpleDateFormat df = new
//			 SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			 dos.writeUTF("Transformers#testUser#" + df.format(new Date())
//			 + "#8.5#TRANSFORMERS: AGE OF EXTINCTION begins after an "
//			 + "epic battle left a great city torn, but with the "
//			 + "world saved. As humanity picks up the pieces, a"
//			 + " shadowy group reveals itself in an attempt to"
//			 + " control the direction of history...while an "
//			 + "ancient, powerful new menace sets Earth in its"
//			 + " crosshairs. With help from a new cast of humans"
//			 + " (led by Mark Wahlberg), Optimus Prime and the"
//			 + " Autobots rise to meet their most fearsome "
//			 + "challenge yet. In an incredible adventure, "
//			 + "they are swept up in a war of good and evil,"
//			 + " ultimately leading to a climactic battle "
//			 + "across the world. (C) Paramount");

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
	 * "start: 2015-02-20 13:48:14#end: 2015-02-20 13:48:14#movie: gozzila#subjectName: "
	 * format
	 * 
	 * @param line
	 *            parsed line
	 * @param arg
	 *            argument ds
	 * @throws ParseException
	 *             throw exception when row is not in assumed type
	 */
	public Arguments parseArgu(String line) throws ParseException {
		if (line == null) {
			return null;
		}
		String[] args = line.split("#");
		int subjectId = 0;
		Date startTime = null;
		Date endTime = null;
		String movieName = "";
		String subjectName = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (String str : args) {
			if (str.contains("subjectId:")) {
				subjectId = Integer.parseInt(str.replaceAll("subjectId:", "")
						.trim());
			} else if (str.contains("start:")) {
				String time = str.replaceAll("start:", "").trim();
				startTime = df.parse(time);
			} else if (str.contains("end:")) {
				String time = str.replaceAll("end:", "").trim();
				endTime = df.parse(time);
			} else if (str.contains("movie:")) {
				movieName = str.replaceAll("movie:", "").trim();
			} else if (str.contains("subjectName:")) {
				subjectName = str.replaceAll("subjectName:", "").trim();
			}
		}
		if (startTime != null && endTime != null && movieName != null) {
			return new Arguments(subjectId, startTime, endTime, movieName,
					subjectName);
		} else {
			return null;
		}
	}
}
