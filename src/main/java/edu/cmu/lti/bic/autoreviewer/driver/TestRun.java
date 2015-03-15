package edu.cmu.lti.bic.autoreviewer.driver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.datastructure.Arguments;
import edu.cmu.lti.bic.autoreviewer.db.DBClassifierHelper;
import edu.cmu.lti.bic.autoreviewer.function.Classifier;

public class TestRun {
	public static void main(String[] args) throws ParseException {
		DBClassifierHelper testHelper = new DBClassifierHelper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = df.parse("2015-03-14 09:05:21");
		Date endTime = df.parse("2015-03-14 09:12:01");

		Arguments testArg = new Arguments(1, startTime, endTime, "test", "test");

		testHelper.dbToCSV(testArg);
		Classifier testClassifier = new Classifier();
		testClassifier.run(testArg);

	}
}
