package edu.cmu.lti.bic.autoreviewer.function;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import edu.cmu.lti.bic.autoreviewer.config.DBTableConfig;
import edu.cmu.lti.bic.autoreviewer.datastructure.Event;
import edu.cmu.lti.bic.autoreviewer.datastructure.MovieEvent;
import edu.cmu.lti.bic.autoreviewer.datastructure.Timeline;
import edu.cmu.lti.bic.autoreviewer.db.DBManager;

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
	public Timeline getTimeline(String movieName) {

		String timeLineTableName = DBTableConfig.MOVIE_TABLE_NAME;
		String movieColumn = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_NAME_INDEX];
		String start = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_START_INDEX];
		String end = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_END_INDEX];
		String description = DBTableConfig.MOVIE_TABLE_STRUCTURE[DBTableConfig.MOVIE_DESCIRPTION];
		String query = "SELECT * FROM " + timeLineTableName + " WHERE "
				+ movieColumn + "='" + movieName+"';";

		DBManager dbMger = new DBManager();
		dbMger.connectDB();

		Timeline newTimeLine = new Timeline();

		ResultSet rst = dbMger.executeSQLQuery(query);
		try {
			while (rst.next()) {
				int eventStartTime = rst.getInt(start);
				int eventEndTime = rst.getInt(end);
				String eventDescription = rst.getString(description);

				Event newEvent = new MovieEvent(eventStartTime, eventEndTime,
						eventDescription);

				newTimeLine.addEvent(newEvent);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTimeLine;
	}

	/***
	 * update timeline.
	 */
	public void update() {

	}
}
