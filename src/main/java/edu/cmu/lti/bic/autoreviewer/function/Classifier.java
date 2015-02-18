package edu.cmu.lti.bic.autoreviewer.function;

import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.ds.Arguments;
import edu.cmu.lti.bic.autoreviewer.ds.ClassifiedData;

/***
 * Adapter between java code and shell classifier code.
 * 
 * @author jhe
 *
 */
public class Classifier {

	private Date startTime;
	private Date endTime;
	private String movie;

	/***
	 * set argument.
	 * @param argu argument ds
	 */
	public final void setClassifierArgument(final Arguments argu) {
		this.startTime = argu.getStartTime();
		this.endTime = argu.getEndTime();
		this.movie = argu.getMovie();
	}

	/***
	 * call shell classifier code and process result.
	 * 
	 * @return classified data.
	 */
	public final ClassifiedData work() {
		
		return null;
	}
}
