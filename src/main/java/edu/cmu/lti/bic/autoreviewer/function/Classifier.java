package edu.cmu.lti.bic.autoreviewer.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.db.DBManager;
import edu.cmu.lti.bic.autoreviewer.ds.Arguments;
import edu.cmu.lti.bic.autoreviewer.ds.ClassifiedData;

/***
 * Adapter between java code and shell classifier code.
 * 
 * @author jhe
 *
 */
public class Classifier {

	/***
	 * call shell classifier code and process result.
	 * 
	 * @param argu
	 *            args.
	 */
	public final void run(Arguments argu) {
		String[] cmd = { "bash", "./start_classifier.sh" };
		File workdir = new File("./apply_script.app/Contents/MacOS/");
		String[] env = {};
		try {
			String errOutput = "";
			Process process = Runtime.getRuntime().exec(cmd, env, workdir);
			String s = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			while ((s = br.readLine()) != null) {
				s += s + "\n";
			}
			System.out.println(s);

			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			while (br2.ready() && (s = br2.readLine()) != null) {
				errOutput += s;
			}
			System.out.println(errOutput);
		} catch (IOException ex) {

		}
	}

	/***
	 * Get classified result.
	 * 
	 * @param argu
	 *            argument of classifier.
	 * @throws ParseException
	 *             some exception.
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @return classified result
	 */
	public final ClassifiedData getClassifiedRst(Arguments argu)
			throws NumberFormatException, SQLException, ParseException {

		String eegTableName = ServerConfiguration.TABLE_NAME;
		String start = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_STARTTIME_INDEX];
		String end = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_ENDTIME_INDEX];
		String subject = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_SUBJECTID_INDEX];
		// TODO score is from a different table
		String score = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_EEGRAW_INDEX];

		String query = "SELECT" + start + score + "FROM" + eegTableName
				+ "WHERE" + subject + "=" + argu.getSubjectId() + "AND" + start
				+ ">" + argu.getStartTime() + "AND" + end + "<"
				+ argu.getEndTime();

		ClassifiedData classifiedData = new ClassifiedData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Date> dates = classifiedData.getTimes();
		ArrayList<Float> scores = classifiedData.getScores();
		DBManager dbMger = new DBManager();

		ResultSet rst = dbMger.executeSQLQuery(query);
		while (rst.next()) {
			dates.add(df.parse(rst.getString(start)));
			scores.add(Float.parseFloat(rst.getString(score)));
		}
		return classifiedData;

	}
}
