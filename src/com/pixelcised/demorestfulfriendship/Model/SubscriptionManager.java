package com.pixelcised.demorestfulfriendship.Model;

import java.sql.*;

import com.pixelcised.demorestfulfriendship.Model.Entity.Subscription;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.net.DatabaseConnectionManager;

public class SubscriptionManager {
	
	private static final String entityTableName = "Subscription";
	
	/*
	 * Insert a record into table Subscription
	 * */
	public boolean addSubscription(Subscription subs) throws SQLException, FriendshipException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
	    
	    try {
	    	// prevent double-subscribing for cleanliness sake
	    	if(hasSubscription(subs) == true) {
	    		throw new FriendshipException("Cannot subscribe because already subscribed");
	    	}
			
		    String query = "INSERT INTO " + DatabaseConnectionManager.getFreeDBName() + 
		    		"." + entityTableName + " VALUES(null, '" + subs.getRequestor() + "', '" + subs.getTarget() + "')";
	    	
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
	 * Queries for a record in table Subscription, given requestor and target
	 * */
	public boolean hasSubscription(Subscription subs) throws SQLException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
		
	    try {
		    String query = "SELECT requestor, target from " + 
		    		DatabaseConnectionManager.getFreeDBName() + "." + entityTableName + 
		    		" WHERE requestor='" + subs.getRequestor() + "' AND target='" + subs.getTarget() + "'";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
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
