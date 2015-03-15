package edu.cmu.lti.bic.autoreviewer.datastructure;

/**
 * Specific event for movie
 * 
 * @author jhe
 *
 */
public class MovieEvent implements Event {

	private int startTime;
	private int endTime;
	private String description;

	public MovieEvent(int pStartTime, int pEndTime, String pDescription) {
		this.startTime = pStartTime;
		this.endTime = pEndTime;
		this.description = pDescription;
	}

	@Override
	public int getStartTime() {
		return startTime;
	}

	@Override
	public int getEndTime() {
		return endTime;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
