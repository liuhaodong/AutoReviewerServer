package edu.cmu.lti.bic.autoreviewer.datastructure;

import java.util.Date;

/***
 * ds for arguments.
 * 
 * @author jhe
 *
 */
public class Arguments {
	private int subjectId;
	private String subjectName;
	private Date startTime;
	private Date endTime;
	private String movie;

	/**
	 * 
	 * @param mSubId
	 * @param mStart
	 * @param mEnd
	 * @param mMovie
	 */
	private void setArguments(int mSubId, Date mStart, Date mEnd,
			String mMovie, String pSubjectName) {
		this.subjectId = mSubId;
		this.startTime = mStart;
		this.endTime = mEnd;
		this.movie = mMovie;
		this.subjectName = pSubjectName;
	}


	public Arguments(int mSubId, Date mStart, Date mEnd, String mMovie,
			String pSubjectName) {
		this.setArguments(mSubId, mStart, mEnd, mMovie, pSubjectName);
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

	/**
	 * @return the subjectId
	 */
	public int getSubjectId() {
		return subjectId;
	}

	/**
	 * @return subject name.
	 */
	public String getSubjectName() {
		return subjectName;
	}

}
