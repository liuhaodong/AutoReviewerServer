package edu.cmu.lti.bic.autoreviewer.ds;

import java.util.ArrayList;
import java.util.Date;

/***
 * Data structure for Timeline
 * @author jhe 
 * 
 */
public class Timeline {
	
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
	 * @param events the events to set
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
	
	
}
