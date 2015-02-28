package edu.cmu.lti.bic.autoreviewer.function;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.cmu.lti.bic.autoreviewer.config.DBTableConfig;
import edu.cmu.lti.bic.autoreviewer.db.DBManager;
import edu.cmu.lti.bic.autoreviewer.ds.Event;
import edu.cmu.lti.bic.autoreviewer.ds.MovieEvent;
import edu.cmu.lti.bic.autoreviewer.ds.Timeline;

/***
 * 
 * @author jhe
 *
 */

public class TimelineManager {

	/***
	 * 
	 * @param movieName
	 *            movie name
	 * @return timeline with name movieName.
	 * @throws SQLException
	 *             exception for sql.
	 * @throws ParseException
	 *             exception for date parse.
	 */
	public Timeline getTimeline(String movieName) throws SQLException,
			ParseException {
		
		String timeLineTableName = DBTableConfig.MOVIE_TABLE_NAME;
		String movieColumn = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_NAME_INDEX];
		String start = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_START_INDEX];
		String end = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_END_INDEX];
		String description = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_DISCIRPTION];
		String query = "SELECT * FROM" + timeLineTableName + "WHERE" + movieColumn + "=" + movieName;
		
		DBManager dbMger = new DBManager();
		Timeline myTime = new Timeline();
		ArrayList<Event> events = myTime.getEvents();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ResultSet rst = dbMger.executeSQLQuery(query);
		while (rst.next()) {
			Event curEvent = new MovieEvent();
			curEvent.setStartTime(df.parse(rst.getString(start)));
			curEvent.setEndTime(df.parse(rst.getString(end)));
			curEvent.setDiscription(rst.getString(description));
			events.add(curEvent);
		}
		return myTime;
	}

	/***
	 * update timeline.
	 */
	public void update() {

	}
}
