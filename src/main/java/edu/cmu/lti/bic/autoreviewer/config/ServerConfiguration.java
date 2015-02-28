package edu.cmu.lti.bic.autoreviewer.config;

/**
 * @author haodongl Configuration class for server.
 */
public final class ServerConfiguration {

	public static final String TABLE_NAME = "EEG_DATA";
	public static final int DEFAULT_PORT = 5000;
	public static final int DEFAULT_REVIEW_PORT = 7000;

	public static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/testdb";

	public static final String DEFAULT_DB_USERNAME = "testuser";
	public static final String DEFAULT_DB_PASSWORD = "test623";

	public static final String DEFAULT_EEG_DATA_DELIMITER = ",";

	public static final int DEFAULT_SUBJECTID_INDEX = 0;
	public static final int DEFUALT_SUBJECTNAME_INDEX = 1;
	public static final int DEFAULT_STARTTIME_INDEX = 2;
	public static final int DEFAULT_ENDTIME_INDEX = 3;
	public static final int DEFAULT_SENSOR_INDEX = 4;
	public static final int DEFAULT_EEGRAW_INDEX = 5;

	public static final String[] EEG_TABLE_STRUCTURE = { "USERNAME",
			"SUBJECT_ID", "START_TIME", "END_TIME", "SENSOR", "ATTENTION",
			"SIGQUAL", "RAWWAVE" };

}
