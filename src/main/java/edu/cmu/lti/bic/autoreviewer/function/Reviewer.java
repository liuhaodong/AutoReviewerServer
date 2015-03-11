package edu.cmu.lti.bic.autoreviewer.function;

import java.util.ArrayList;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.ds.ClassifiedData;
import edu.cmu.lti.bic.autoreviewer.ds.Event;
import edu.cmu.lti.bic.autoreviewer.ds.ReviewResult;
import edu.cmu.lti.bic.autoreviewer.ds.Timeline;

/***
 * 
 * @author jhe
 *
 */

public class Reviewer {

	private Timeline timeLine;
	private ClassifiedData data;
	private static final float HIGH_THREASHOLD = ServerConfiguration.DEFAULT_HIGH_VALUE;
	private static final float LOW_THREASHOLD = ServerConfiguration.DEFAULT_LOW_VALUE;
	private static final int WINDOW_SIZE = ServerConfiguration.DEFAULT_WINDOW_SIZE;

	/***
	 * set arguments for reviewer.
	 * 
	 * @param mTime
	 *            timeline specified.
	 * @param mData
	 *            classified data specified
	 */
	public final void setReviewArgument(final Timeline mTime,
			final ClassifiedData mData) {
		this.timeLine = mTime;
		this.data = mData;
	}

	/***
	 * use timeline and classified data to generate review result.
	 * 
	 * @return proceded review result
	 */
	public final ReviewResult run() {
		ReviewResult reviewRst = new ReviewResult();
		ArrayList<Date> times = data.getTimes();
		ArrayList<Float> scores = data.getScores();
		int timeLineIdx = 0;
		int dataTimeIdx = 0;
		while (timeLineIdx < timeLine.getEvents().size()
				&& dataTimeIdx < data.getTimes().size()) {
			Event curEvent = timeLine.getEvents().get(timeLineIdx);
			float sum = (float) 0;
			int num = 0;
			while (dataTimeIdx < data.getTimes().size()
					&& data.getTimes().get(dataTimeIdx)
							.before(curEvent.getEndTime())) {
				if (data.getTimes().get(dataTimeIdx)
						.after(curEvent.getStartTime())) {
					sum += data.getScores().get(dataTimeIdx);
					num++;
				}
				dataTimeIdx++;
			}
			this.generateReview(reviewRst, sum / (float) num, curEvent);
			timeLineIdx++;
		}
		return reviewRst;
	}

	/***
	 * generate review with different score average.
	 * 
	 * @param rvwRst
	 *            review result.
	 * @param avg
	 *            average.
	 * @param curEvent
	 *            current event.
	 */
	public void generateReview(ReviewResult rvwRst, float avg, Event curEvent) {
		String highDescription = "User is exciting when :";
		String midDescription = "User is with no strong emotion when :";
		String lowDescription = "User feels boring when :";

		rvwRst.getTimes().add(curEvent.getStartTime());
		if (avg > HIGH_THREASHOLD) {
			rvwRst.getDiscriptions().add(
					highDescription + curEvent.getDiscription());
		} else if (avg > LOW_THREASHOLD) {
			rvwRst.getDiscriptions().add(
					midDescription + curEvent.getDiscription());
		} else {
			rvwRst.getDiscriptions().add(
					lowDescription + curEvent.getDiscription());
		}
	}
}
