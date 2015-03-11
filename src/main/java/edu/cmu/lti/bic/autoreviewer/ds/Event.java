package edu.cmu.lti.bic.autoreviewer.ds;

import java.util.Date;

/**
 * Event
 * 
 * @author jhe
 *
 */
public class Event {
	private Date startTime;
	private Date endTime;
	private String discription;

	/**
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param pStartTime
	 *            the startTime to set
	 */
	public void setStartTime(Date pStartTime) {
		this.startTime = pStartTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param pEndTime
	 *            the endTime to set
	 */
	public void setEndTime(Date pEndTime) {
		this.endTime = pEndTime;
	}

	/**
	 * @return the discription
	 */
	public String getDiscription() {
		return discription;
	}

	/**
	 * @param pDiscription
	 *            the discription to set
	 */
	public void setDiscription(String pDiscription) {
		this.discription = pDiscription;
	}
}
