package edu.cmu.lti.bic.autoreviewer.ds;

import java.util.Date;


/***
 * ds for arguments.
 * @author jhe
 *
 */
public class Arguments {
	private Date startTime;
	private Date endTime;
	private String movie;
	
	/***
	 * set argument.
	 * @param mStart start time 
	 * @param mEnd end time
	 * @param mMovie movie name
	 */
	public final void setArguments(Date mStart, Date mEnd, String mMovie) {
		this.startTime = mStart;
		this.endTime = mEnd;
		this.movie = mMovie;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @return the movie
	 */
	public String getMovie() {
		return movie;
	}
}
