package edu.cmu.lti.bic.autoreviewer.ds;

import java.util.ArrayList;

/***
 * Data structure for Timeline
 * 
 * @author jhe
 * 
 */
public class Timeline {

	/**
	 * hehe
	 */
	private ArrayList<Event> events;

	/***
	 * constructor.
	 */
	public Timeline() {
		events = new ArrayList<Event>();
	}

	/**
	 * @return the events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * @param pEvents
	 *            the events to set
	 */
	public void setEvents(ArrayList<Event> pEvents) {
		this.events = pEvents;
	}

}
