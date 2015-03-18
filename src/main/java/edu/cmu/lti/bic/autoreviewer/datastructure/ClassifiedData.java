package edu.cmu.lti.bic.autoreviewer.datastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;

/***
 * Data structure for classified data
 * 
 * @author jhe
 *
 */
public class ClassifiedData {

	private int timeInterval;
	private List<Boolean> engageResult;
	
	private List<Double> rawResult;

	public ClassifiedData(String resultFilePath) {
		this.timeInterval = ServerConfiguration.DEFAULT_TASK_INTERVAL;
		engageResult = new ArrayList<Boolean>();
		rawResult = new ArrayList<Double>();
		this.buildResultList(resultFilePath);
	}

	public ClassifiedData(String resultFilePath, int customInterval) {
		this.timeInterval = customInterval;
		engageResult = new ArrayList<Boolean>();
		rawResult = new ArrayList<Double>();
		this.buildResultList(resultFilePath);
	}

	public double getTotalEngagePercentage() {
		int sum = 0;
		for (Boolean tmp : engageResult) {
			if (tmp) {
				sum++;
			}
		}

		return sum / (double) engageResult.size();
	}

	public double getEngagePercentage(int startTimeSec, int endTimeSec) {

		if (endTimeSec - startTimeSec < timeInterval) {
			return 0;
		}

		int startIndex = startTimeSec / timeInterval;
		int endIndex = endTimeSec / timeInterval;

		int total = endIndex - startIndex;
		int sum = 0;
		for (int i = startIndex; i < endIndex; i++) {
			if (engageResult.get(i)) {
				sum++;
			}
		}

		return (double) sum / total;
	}

	private void buildResultList(String resultFilePath) {
		try {
			BufferedReader resultReader = new BufferedReader(new FileReader(
					resultFilePath));

			String line = "";
			int lastLineIndex = 0;
			while ((line = resultReader.readLine()) != null) {
				String[] data = line.split(",");
				int lineIndex = Integer.parseInt(data[0]);
				if (lastLineIndex == 0) {
					lastLineIndex = lineIndex;
				}

				double negativeProb = Double.parseDouble(data[1]);
				double positiveProb = Double.parseDouble(data[2]);
				boolean lineResult = (positiveProb >= negativeProb);

				if (lineIndex - lastLineIndex > 1) {
					for (int i = 0; i < lineIndex - lastLineIndex - 1; i++) {
						engageResult.add(false);
					}
				}

				lastLineIndex = lineIndex;

				engageResult.add(lineResult);
				rawResult.add(positiveProb);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getTimeInterval(){
		return this.timeInterval;
	}
	
	public List<Double> getRawData(){
		return this.rawResult;
	}

}
