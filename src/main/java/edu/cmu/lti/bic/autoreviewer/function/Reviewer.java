package edu.cmu.lti.bic.autoreviewer.function;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.datastructure.Arguments;
import edu.cmu.lti.bic.autoreviewer.datastructure.ClassifiedData;
import edu.cmu.lti.bic.autoreviewer.datastructure.Event;
import edu.cmu.lti.bic.autoreviewer.datastructure.ReviewResult;
import edu.cmu.lti.bic.autoreviewer.datastructure.Timeline;

/***
 * 
 * @author jhe
 *
 */

public class Reviewer {
	private Reviewer myReviewer;

	private static final float HIGH_THREASHOLD = ServerConfiguration.DEFAULT_HIGH_VALUE;
	private static final float LOW_THREASHOLD = ServerConfiguration.DEFAULT_LOW_VALUE;
	private static final int WINDOW_SIZE = ServerConfiguration.DEFAULT_WINDOW_SIZE;

	private Reviewer() {

	}

	public Reviewer getReviewer() {
		if (myReviewer == null) {
			myReviewer = new Reviewer();
		}
		return myReviewer;
	}

	public static ReviewResult generateReview(Timeline pTimeline,
			ClassifiedData pData, Arguments pArg) {

		ReviewResult reviewResult = new ReviewResult(pArg.getMovie(),
				pArg.getSubjectName());

		List<Event> timeLineEvents = pTimeline.getEvents();

		String engaged = "The user was interested in the plot that: ";
		String notEngaged = "The user was not interested in the plot that: ";
		String mediocre = "The user didn't have strong feeling for the plot that: ";

		int engageSum = 0;

		for (Event tmpEvent : timeLineEvents) {
			String eventDescription = tmpEvent.getDescription();
			int eventStartTime = tmpEvent.getStartTime();
			int eventEndTime = tmpEvent.getEndTime();
			double tmpScore = pData.getEngagePercentage(eventStartTime,
					eventEndTime);

			if (tmpScore > HIGH_THREASHOLD) {
				reviewResult
						.addReviewSegment(engaged + eventDescription + "$$");
				engageSum++;
			} else if (tmpScore < LOW_THREASHOLD) {
				reviewResult.addReviewSegment(notEngaged + eventDescription
						+ "$$");
			} else {
				reviewResult.addReviewSegment(mediocre + eventDescription
						+ "$$");
			}

			reviewResult.setReviewScore(pData.getTotalEngagePercentage());

		}

		return reviewResult;
	}
}
