package com.pixelcised.demorestfulfriendship.Model;

import java.sql.*;

import com.pixelcised.demorestfulfriendship.Model.Entity.FriendConnection;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.net.DatabaseConnectionManager;

public class FriendConnectionManager {
	
	private static final String entityTableName = "Connection";
	
	/*
	 * Insert a record into table Connection
	 * */
	public boolean addFriendConnection(FriendConnection fc) throws SQLException, FriendshipException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
	    
	    try {
	    	// prevent double-befriending for cleanliness sake
	    	if(hasFriendConnection(fc) == true) {
	    		throw new FriendshipException("Cannot add friend connection because already connected");
	    	}
	    	
		    boolean success = true;
		    String query = "INSERT INTO " + DatabaseConnectionManager.getFreeDBName() + 
		    		"." + entityTableName + " VALUES(null, '" + fc.getUserID() + "', '" + fc.getFriendID() + "')";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        stmt.executeUpdate(query);
	        return success;
	    }
	    catch (SQLException e ) {
	        System.out.println(e.toString());
	        throw e;
	    }
	    finally {
	        if (stmt != null) {
	        	stmt.close();
	        }
	    }
	}
	
	
	/*
	 * Queries for a record in table Connection
	 * */
	public boolean hasFriendConnection(FriendConnection fc) throws SQLException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
		
	    try {
		    String query = "SELECT userID, friendID from " + 
		    		DatabaseConnectionManager.getFreeDBName() + "." + entityTableName + 
		    		" WHERE userID='" + fc.getUserID() + "' AND friendID='" + fc.getFriendID() + "'";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        // check for at least 1 record in resultset
	        boolean found = false;
	        while (rs.next()) {
	        	found = true;
	        	break;
	        }
        	return found;
	    }
	    catch (SQLException e ) {
	        System.out.println(e.toString());
	        throw e;
	    }
	    finally {
	        if (stmt != null) {
	        	stmt.close();
	        }
	    }
	    
	}
	
}
