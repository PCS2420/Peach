package br.com.poli.peachproject.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLConnector {
	private static Connection connect = null;
	/*public static final String DB_NAME = "sql378815";
	public static final String DB_HOST = "jdbc:mysql://sql3.freemysqlhosting.net:3306/" + DB_NAME;
	public static final String DB_USER = "sql378815";
	public static final String DB_PASS = "fA5%uN1%";*/
	public static final String DB_NAME = "mydb";
	public static final String DB_HOST = "jdbc:mysql://localhost:3306/" + DB_NAME;
	public static final String DB_USER = "root";
	public static final String DB_PASS = "Super900.";
	
	
	public static Connection openConnection() throws ClassNotFoundException, SQLException {
		 // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS); 
	      return connect;
	}
	
	public static void closeConnection() throws SQLException {
		connect.close();
	}
	
}
