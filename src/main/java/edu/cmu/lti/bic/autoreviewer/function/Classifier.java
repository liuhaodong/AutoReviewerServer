package edu.cmu.lti.bic.autoreviewer.function;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.config.DBTableConfig;
import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.db.DBManager;
import edu.cmu.lti.bic.autoreviewer.ds.Arguments;
import edu.cmu.lti.bic.autoreviewer.ds.ClassifiedData;
import edu.cmu.lti.bic.autoreviewer.ds.Event;
import edu.cmu.lti.bic.autoreviewer.ds.MovieEvent;

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
	 * @return classified data.
	 */
	public final void run(Arguments argu) {
		
		
	}
	
	/***
	 * Get classified result.
	 * @param argu argument of classifier.
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public final ClassifiedData getClassifiedRst(Arguments argu) throws NumberFormatException, SQLException, ParseException {
		
		String eegTableName = ServerConfiguration.TABLE_NAME;
		String start = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_STARTTIME_INDEX];
		String end = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_ENDTIME_INDEX];
		String subject = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_SUBJECTID_INDEX];
		// TODO score is from a different table
		String score = ServerConfiguration.EEG_TABLE_STRUCTURE[ServerConfiguration.DEFAULT_EEGRAW_INDEX];
		
		String query = "SELECT" + start + score  + "FROM" + eegTableName + "WHERE" + subject + "=" +argu.getSubjectId() + "AND" + start + ">" + argu.getStartTime() + "AND" + end + "<" + argu.getEndTime();
		
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
