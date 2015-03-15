package edu.cmu.lti.bic.autoreviewer.config;

/**
 * @author haodongl Configuration class for server.
 */
public final class ServerConfiguration {

	public static final String TABLE_NAME = "D";
	public static final int DEFAULT_PORT = 5000;
	public static final int DEFAULT_REVIEW_PORT = 7000;

	public static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/testdb";

	public static final String DEFAULT_DB_USERNAME = "testuser";
	public static final String DEFAULT_DB_PASSWORD = "test623";

	public static final String DEFAULT_EEG_DATA_DELIMITER = ",";

	public static final int DEFAULT_SUBJECTID_INDEX = 3;
	public static final int DEFUALT_SUBJECTNAME_INDEX = 2;
	public static final int DEFAULT_STARTTIME_INDEX = 4;
	public static final int DEFAULT_ENDTIME_INDEX = 5;
	public static final int DEFAULT_SENSOR_INDEX = 6;
	public static final int DEFAULT_EEGRAW_INDEX = 9;
	
	public static final int INPUT_EEG_SUBJECTID_INDEX = 0;
	public static final int INPUT_EEG_USERNAME_INDEX = 1;
	public static final int INPUT_EEG_STARTTIME_INDEX = 2;
	public static final int INPUT_EEG_ENDTIME_INDEX = 3;
	public static final int INPUT_EEG_SENSOR_INDEX = 4;
	public static final int INPUT_EEG_EEGRAW_INDEX = 5;

	public static final String[] EEG_TABLE_STRUCTURE = { "USERNAME",
			"SUBJECT_ID", "START_TIME", "END_TIME", "SENSOR", "ATTENTION",
			"SIGQUAL", "RAWWAVE" };

	public static final float DEFAULT_HIGH_VALUE = (float) 0.6;
	public static final float DEFAULT_LOW_VALUE = (float) 0.3;
	public static final int DEFAULT_WINDOW_SIZE = 10;

	public static final int DEFAULT_TASK_INTERVAL = 5;
	public static final int DEFAULT_REST_TIME = 20;

	public static final String DEFAULT_CLASSIFIED_DATA_PATH = "./external_binary/apply_script.app/Contents/MacOS/outs.csv";

}
