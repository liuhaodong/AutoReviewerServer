package edu.cmu.lti.bic.autoreviewer.config;

/**
 * DB configuration.
 * @author jhe
 *
 */
public class DBTableConfig {
	
	public static final String MOVIE_TABLE_NAME = "movie_info";
	
	public static final int MOVIE_NAME_INDEX = 0;
	public static final int MOVIE_START_INDEX = 1;
	public static final int MOVIE_END_INDEX = 2;
	public static final int MOVIE_DESCIRPTION = 3;
	
	public static final String[] MOVIE_TABLE_STRUCTURE = {"MOVIE_NAME", "START_TIME", "END_TIME","DESCRIPTION"};
	
}
