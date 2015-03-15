package edu.cmu.lti.bic.autoreviewer.datastructure;

/**
 * Event
 * 
 * @author jhe
 *
 */
public interface Event {
	int getStartTime();

	int getEndTime();

	String getDescription();
}
