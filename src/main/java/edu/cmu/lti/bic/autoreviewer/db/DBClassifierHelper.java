package edu.cmu.lti.bic.autoreviewer.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.datastructure.Arguments;

public class DBClassifierHelper {
	private DBManager dbManager;

	public DBClassifierHelper() {
		dbManager = new DBManager();
	}

	private static final int FOUR = 4;

	public void dbToCSV(Arguments args) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = df.format(args.getStartTime());

		String endTime = df.format(args.getEndTime());

		int subjectId = args.getSubjectId();

		String sqlStatement = "select * from EEG_DATA where START_TIME between '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "' AND SUBJECT_ID="
				+ subjectId + ";";

		dbManager.connectDB();

		ResultSet resultSet = dbManager.executeSQLQuery(sqlStatement);

		BufferedWriter[] bw = new BufferedWriter[FOUR];
		String[] fileNames = {
				"./external_binary/apply_script.app/Contents/data/FP1.xls",
				"./external_binary/apply_script.app/Contents/data/FP2.xls",
				"./external_binary/apply_script.app/Contents/data/TP9.xls",
				"./external_binary/apply_script.app/Contents/data/TP10.xls" };

		String line;
		try {
			for (int i = 0; i < FOUR; i++) {
				bw[i] = new BufferedWriter(new FileWriter(fileNames[i]));
				bw[i].write("machine	subject	start_time	end_time	sigqual	rawwave\n");
			}
			while (resultSet.next()) {
				String machine = "machine";
				String subject = args.getSubjectName();
				String startTimeString = resultSet
						.getString(ServerConfiguration.DEFAULT_STARTTIME_INDEX)
						+ "00";
				String endTimeString = resultSet
						.getString(ServerConfiguration.DEFAULT_ENDTIME_INDEX)
						+ "00";
				String rawWave = resultSet
						.getString(ServerConfiguration.DEFAULT_EEGRAW_INDEX);
				String sigqual = "0";
				String channel = resultSet
						.getString(ServerConfiguration.DEFAULT_SENSOR_INDEX);
				line = machine + '\t' + subject + '\t' + startTimeString + '\t'
						+ endTimeString + '\t' + sigqual + '\t' + rawWave
						+ '\n';
				switch (channel) {
				case "ch0":
					bw[0].write(line);
					break;
				case "ch1":
					bw[1].write(line);
					break;
				case "ch2":
					bw[2].write(line);
					break;
				case "ch3":
					bw[3].write(line);
					break;
				default:
					line = "";
					break;
				}

			}
			for (int i = 0; i < FOUR; i++) {
				bw[i].flush();
				bw[i].close();
			}

			// Create Task file

			String taskFile = "./external_binary/apply_script.app/Contents/data/task.xls";

			BufferedWriter bWriter = new BufferedWriter(
					new FileWriter(taskFile));
			String taskLine = "";
			taskLine = "machine	subject	stim	block	start_time	end_time	cond	score\n";
			bWriter.write(taskLine);

			int taskSeconds = (int) (args.getEndTime().getTime() - args
					.getStartTime().getTime()) / 1000;

			int taskNum = taskSeconds
					/ ServerConfiguration.DEFAULT_TASK_INTERVAL;

			String machine = "machine";
			String tmpLine = "";
			for (int i = 0; i < taskNum; i++) {
				Date tmpStartDate = new Date();
				Date tmpEndDate = new Date();

				tmpStartDate.setTime(args.getStartTime().getTime() + 1000
						* ServerConfiguration.DEFAULT_TASK_INTERVAL * i);

				tmpEndDate.setTime(args.getStartTime().getTime() + (i + 1)
						* 1000 * (ServerConfiguration.DEFAULT_TASK_INTERVAL));

				if (i < ServerConfiguration.DEFAULT_REST_TIME
						/ ServerConfiguration.DEFAULT_TASK_INTERVAL) {
					line = machine + '\t' + "test" + '\t' + "stim" + '\t'
							+ "block" + '\t' + df.format(tmpStartDate) + '\t'
							+ df.format(tmpEndDate) + '\t' + "3" + '\t' + "0\n";
				} else {

					line = machine + '\t' + "test" + '\t' + "stim" + '\t'
							+ "block" + '\t' + df.format(tmpStartDate) + '\t'
							+ df.format(tmpEndDate) + '\t' + "0" + '\t' + "0\n";
				}
				bWriter.write(line);
			}

			bWriter.flush();
			bWriter.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void csvToDB() {

	}

}
