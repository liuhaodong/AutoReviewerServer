package edu.cmu.lti.bic.autoreviewer.function;

import edu.cmu.lti.bic.autoreviewer.ds.ClassifiedData;
import edu.cmu.lti.bic.autoreviewer.ds.ReviewResult;
import edu.cmu.lti.bic.autoreviewer.ds.Timeline;

/***
 * 
 * @author jhe
 *
 */

public class Reviewer {

	private Timeline timel;
	private ClassifiedData data;

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
		this.timel = mTime;
		this.data = mData;
	}

	/***
	 * use timeline and classified data to generate review result.
	 * 
	 * @return proceded review result
	 */
	public final ReviewResult work() {
		
		return null;
	}

}
