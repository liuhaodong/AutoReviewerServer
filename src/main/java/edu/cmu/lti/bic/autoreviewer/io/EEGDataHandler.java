package edu.cmu.lti.bic.autoreviewer.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import edu.cmu.lti.bic.autoreviewer.config.ServerConfiguration;
import edu.cmu.lti.bic.autoreviewer.db.DBManager;

public class EEGDataHandler implements Runnable {

	private Socket socket;

	DBManager dbManager;

	public EEGDataHandler(Socket pSocket) {
		this.socket = pSocket;
		dbManager = new DBManager();
	}

	@Override
	public void run() {
		try {
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));

			String line;

			dbManager.connectDB();

			while ((line = inStream.readLine()) != null) {
				this.storeEEGData(line);
			}

			dbManager.closeDB();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private double[] parseEEGData(String eegData) {

		String[] eegArray = eegData
				.split(ServerConfiguration.DEFAULT_EEG_DATA_DELIMITER);

		double[] resultArray = new double[eegArray.length];

		return resultArray;

	}

	private void storeEEGData(String eegData) {
		
		System.out.println(eegData);
		String[] eegArray = eegData
				.split(ServerConfiguration.DEFAULT_EEG_DATA_DELIMITER);
		int subjectID = Integer
				.parseInt(eegArray[ServerConfiguration.DEFAULT_SUBJECTID_INDEX]);
		String subjectName = eegArray[ServerConfiguration.DEFAULT_STARTTIME_INDEX];
		String startDate = eegArray[ServerConfiguration.DEFAULT_STARTTIME_INDEX];
		String endDate = eegArray[ServerConfiguration.DEFAULT_ENDTIME_INDEX];
		String sensor = eegArray[ServerConfiguration.DEFAULT_SENSOR_INDEX];
		String rawData = eegArray[ServerConfiguration.DEFAULT_EEGRAW_INDEX];

		StringBuilder statement = new StringBuilder();
		statement
				.append("insert into EEG_DATA (USERNAME, SUBJECT_ID, START_TIME, END_TIME, SENSOR, ATTENTION,SIGQUAL, RAWWAVE) values ");
		statement.append("('" + subjectName + "',");
		statement.append(subjectID + ",");
		statement.append("'" + startDate + "',");
		statement.append("'" + endDate + "',");
		statement.append("'" + sensor + "',");
		statement.append("'" + (-1) + "',");
		statement.append("'" + (-1) + "',");
		statement.append("'" + rawData + "'");
		statement.append(");");

		dbManager.executeSQLUpdate(statement.toString());
	}

}
