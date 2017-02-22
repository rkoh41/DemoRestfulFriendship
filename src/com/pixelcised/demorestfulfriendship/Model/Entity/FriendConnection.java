package com.pixelcised.demorestfulfriendship.Model.Entity;

public class FriendConnection {
	
	private String userID;
	private String friendID;
	
	public FriendConnection() {
		
	}
	
	public FriendConnection(String userID, String friendID) {
		this.userID = userID;
		this.friendID = friendID;
	}
	
	public FriendConnection(String friendID) {
		this.friendID = friendID;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public String getFriendID() {
		return friendID;
	}
	
}
