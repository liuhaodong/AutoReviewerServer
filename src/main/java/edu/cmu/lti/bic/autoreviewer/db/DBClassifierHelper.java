package edu.cmu.lti.bic.autoreviewer.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.ds.Arguments;

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

		String sqlStatement = "select * from EEG_DATA where START_TIME between "
				+ startTime
				+ " and "
				+ endTime
				+ " AND SUBJECT_ID="
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
						.getString(ServerConfiguration.DEFAULT_STARTTIME_INDEX);
				String endTimeString = resultSet
						.getString(ServerConfiguration.DEFAULT_ENDTIME_INDEX);
				String rawWave = resultSet
						.getString(ServerConfiguration.DEFAULT_EEGRAW_INDEX);
				String sigqual = "sigqual";
				String channel = resultSet
						.getString(ServerConfiguration.DEFAULT_SENSOR_INDEX);
				line = machine + '\t' + subject + '\t' + startTimeString + '\t'
						+ endTimeString + '\t' + sigqual + '\t' + rawWave
						+ '\n';
				switch (channel) {
				case "ch1":
					bw[0].write(line);
					break;
				case "ch2":
					bw[1].write(line);
					break;
				case "ch3":
					bw[2].write(line);
					break;
				case "ch4":
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
