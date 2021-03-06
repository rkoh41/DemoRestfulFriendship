package com.pixelcised.demorestfulfriendship.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.pixelcised.demorestfulfriendship.Model.Entity.FriendConnection;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.net.DatabaseConnectionManager;
import com.pixelcised.demorestfulfriendship.Model.BlockageManager;
import com.pixelcised.demorestfulfriendship.Model.Entity.Blockage;

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
	    	else {
		    	// check for blockage status
	    		// both directions of blockage are valid between 2 users
		    	BlockageManager bm = new BlockageManager(); 
		    	Blockage blocDirection1 = new Blockage(fc.getUserID(), fc.getFriendID());
		    	Blockage blocDirection2 = new Blockage(fc.getFriendID(), fc.getUserID());
		    	if(bm.hasBlockage(blocDirection1) || bm.hasBlockage(blocDirection2)) {
		    		throw new FriendshipException("Cannot add friend connection because one party has blocked the other");
		    	}
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
	
	/*
	 * Queries for a list of friendIDs in table Connection given userID
	 * */
	public List<FriendConnection> getFriendList(String userID) throws SQLException {
		
		List<FriendConnection> friendList = new ArrayList<FriendConnection>();
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
		
	    try {
		    String query = "SELECT userID, friendID from " + 
		    		DatabaseConnectionManager.getFreeDBName() + "." + entityTableName + 
		    		" WHERE userID='" + userID +"'";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	friendList.add(new FriendConnection(rs.getString("userID"), rs.getString("friendID")));
	        }
	        return friendList;
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
	 * Queries for a list of mutual friend records in table Connection, given 2 email IDs
	 * */
	public List<FriendConnection> getCommonFriends(FriendConnection fc) throws SQLException {
		
		List<FriendConnection> friendList = new ArrayList<FriendConnection>();
		Connection con = DatabaseConnectionManager.getDBConnection();
	    Statement stmt = null;
		
	    try {
		    String query = "SELECT friendID FROM " + 
		    		DatabaseConnectionManager.getFreeDBName() + "." + entityTableName + 
		    		" WHERE userID IN ( '" + fc.getUserID() + "', '" + fc.getFriendID() + "'" + 
		    		" ) GROUP BY friendID HAVING COUNT(friendID) >= 2";
	    	System.out.println("query = " + query);
	    	
	        stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	// FriendConnection.userID is null in this case
	        	friendList.add(new FriendConnection(rs.getString("friendID")));
	        }
	        return friendList;
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
