package edu.cmu.lti.bic.autoreviewer.datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/***
 * Data structure for Timeline
 * 
 * @author jhe
 * 
 */
public class Timeline {
	private List<Event> events;

	public Timeline() {
		events = new LinkedList<Event>();
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public boolean addEvent(Event newEvent) {
		return events.add(newEvent);
	}

	public int getEventsNum() {
		return this.events.size();
	}

}
