package com.pixelcised.demorestfulfriendship.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
	
	/*
	 * Deletes a record from table Subscription
	 * */
	public boolean removeSubscription(Subscription subs) throws SQLException {
		
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
	    
	    try {
		    String query = "DELETE FROM " + DatabaseConnectionManager.getFreeDBName() + 
		    		"." + entityTableName + " WHERE requestor='" + subs.getRequestor() + 
		    		"' AND target='" + subs.getTarget() + "'";
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
	 * Queries for a list of Subscriptions in table Subscription
	 * */
	public List<Subscription> getSubscriberList(String userID) throws SQLException {
		
		List<Subscription> subscriptionList = new ArrayList<Subscription>();
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
		
	    try {
		    String query = "SELECT requestor from " + 
		    		DatabaseConnectionManager.getFreeDBName() + "." + entityTableName + 
		    		" WHERE target='" + userID +"'";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	// Subscription.target is null in this case
	        	subscriptionList.add(new Subscription(rs.getString("requestor")));
	        }
	        return subscriptionList;
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
