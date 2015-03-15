package edu.cmu.lti.bic.autoreviewer.driver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.datastructure.Arguments;
import edu.cmu.lti.bic.autoreviewer.datastructure.ClassifiedData;
import edu.cmu.lti.bic.autoreviewer.datastructure.ReviewResult;
import edu.cmu.lti.bic.autoreviewer.datastructure.Timeline;
import edu.cmu.lti.bic.autoreviewer.function.Reviewer;
import edu.cmu.lti.bic.autoreviewer.function.TimelineManager;

public class TestReviewRun {
	public static void main(String[] args) throws ParseException {
		TimelineManager tmManager = new TimelineManager();

		ClassifiedData classifiedData = new ClassifiedData(
				ServerConfiguration.DEFAULT_CLASSIFIED_DATA_PATH);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date startTime = df.parse("2015-03-14 09:05:21");
		Date endTime = df.parse("2015-03-14 09:12:01");

		Arguments argu = new Arguments(1, startTime, endTime, "La Luna", "test");

		Timeline myTime = tmManager.getTimeline(argu.getMovie());

		ReviewResult myResult = Reviewer.generateReview(myTime, classifiedData,
				argu);

		System.out.println(myResult.getReview());
	}
}
