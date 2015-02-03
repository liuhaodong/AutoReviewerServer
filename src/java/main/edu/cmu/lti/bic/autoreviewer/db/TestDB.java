package edu.cmu.lti.bic.autoreviewer.db;

public class TestDB {
	public static void main(String[] args) throws Exception {
	    MySQLAccess dao = new MySQLAccess();
	    dao.readDataBase();
	  }
}
