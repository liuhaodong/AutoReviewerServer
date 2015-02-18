package edu.cmu.lti.bic.autoreviewer.ds;

import java.util.ArrayList;
import java.util.Date;

/***
 * Data structure for classified data
 * @author jhe
 *
 */
public class ClassifiedData {
	private ArrayList<Date> times;
	private ArrayList<Float> scores;
	
	/**
	 * @return the times
	 */
	public ArrayList<Date> getTimes() {
		return times;
	}
	/**
	 * @param mTimes the times to set
	 */
	public void setTimes(ArrayList<Date> mTimes) {
		this.times = mTimes;
	}
	/**
	 * @return the scores
	 */
	public ArrayList<Float> getScores() {
		return scores;
	}
	/***
	 * 
	 * @param mScores scores
	 */
	public void setScores(ArrayList<Float> mScores) {
		this.scores = mScores;
	}
	
	
}
