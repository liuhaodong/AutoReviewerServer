package edu.cmu.lti.bic.autoreviewer.ds;

import java.util.ArrayList;
import java.util.Date;

/***
 * Data structure for result of movie review
 * @author jhe 
 * 
 */
public class ReviewResult {
	private ArrayList<Date> times;
	private ArrayList<String> discriptions;
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
	 * @return the discriptions
	 */
	public ArrayList<String> getDiscriptions() {
		return discriptions;
	}
	/**
	 * @param mDiscriptions the discriptions to set
	 */
	public void setDiscriptions(ArrayList<String> mDiscriptions) {
		this.discriptions = mDiscriptions;
	}
	/**
	 * @return the scores
	 */
	public ArrayList<Float> getScores() {
		return scores;
	}
	/**
	 * @param mScores the scores to set
	 */
	public void setScores(ArrayList<Float> mScores) {
		this.scores = mScores;
	}
}
