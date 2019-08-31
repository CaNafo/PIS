package helpers;

import dbManipulation.DBManipulation;
import dbType.DatabaseType;

public class DatabaseConnection extends DBManipulation {

	private static DatabaseConnection conn=null;
	
	private static DatabaseType connType = DatabaseType.MsSQL_JDBC;
	private static String adress = "78.28.157.8";
	private static String port = "1433";
	private static String dbName = "PIS2019";
	private static String dbUser = "EtfPIS2019G2";
	private static String dbPass = "EtfPIS2019G21982";
	
	public DatabaseConnection(DatabaseType connType, String adress, String port, String dbName, String dbUser,
			String dbPass) {
		super(connType, adress, port, dbName, dbUser, dbPass);
	}


	public static DatabaseConnection getInstance() {
		if(conn==null)
			conn=new DatabaseConnection(connType, adress, port, dbName, dbUser, dbPass);
		return conn;
	}
}
