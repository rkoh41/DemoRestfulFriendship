package com.pixelcised.demorestfulfriendship.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
	
	private static final String freeDBDbms = "mysql"; 
	private static final String freeDBUri = "sql6.freemysqlhosting.net"; 
	private static final String freeDBPort = "3306"; 
	private static final String freeDBName = "sql6159574"; 
	private static final String freeDBUsername = "sql6159574";
	private static final String freeDBPassword = "x55CWMySLQ";

	public static Connection getDBConnection() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
		Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", freeDBUsername);
	    connectionProps.put("password", freeDBPassword);
	    try {
	    	conn = DriverManager.getConnection("jdbc:" + freeDBDbms + 
	    			"://" + freeDBUri + ":" + freeDBPort + "/", connectionProps);
		    System.out.println("Connected to database");
	    }
	    catch (SQLException sqle) {
	        System.out.println(sqle.toString());
	    }
	    
	    return conn;
	}
	
	public static String getFreeDBName() {
		return freeDBName;
	}
	
}
