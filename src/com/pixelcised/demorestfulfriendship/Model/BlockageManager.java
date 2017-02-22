package com.pixelcised.demorestfulfriendship.Model;

import java.sql.*;

import com.pixelcised.demorestfulfriendship.Model.Entity.Blockage;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.net.DatabaseConnectionManager;

public class BlockageManager {
	
	private static final String entityTableName = "Blockage";	//as per DB table name
	
	/*
	 * Insert a record into table Blockage
	 * */
	public boolean addBlockage(Blockage bloc) throws SQLException, FriendshipException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
	    
	    try {
	    	// prevent double-blocking for cleanliness sake
	    	if(hasBlockage(bloc) == true) {
	    		throw new FriendshipException("Cannot block because already blocked");
	    	}
	    	
		    String query = "INSERT INTO " + DatabaseConnectionManager.getFreeDBName() + 
		    		"." + entityTableName + " VALUES(null, '" + bloc.getRequestor() + "', '" + bloc.getTarget() + "')";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        stmt.executeUpdate(query);
	        return true;
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
	 * Queries a given record in table Blockage
	 * */
	public boolean hasBlockage(Blockage bloc) throws SQLException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
		
	    try {
		    String query = "SELECT requestor, target from " + 
		    		DatabaseConnectionManager.getFreeDBName() + "." + entityTableName + 
		    		" WHERE requestor='" + bloc.getRequestor() + "' AND target='" + bloc.getTarget() + "'";
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

