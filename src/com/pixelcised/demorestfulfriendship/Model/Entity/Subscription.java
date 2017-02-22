package com.pixelcised.demorestfulfriendship.Model.Entity;

public class Subscription {
	
	private String requestor;
	private String target;
	
	public Subscription() {
		
	}
	
	public Subscription(String requestor, String target) {
		this.requestor = requestor;
		this.target = target;
	}
	
	public Subscription(String requestor) {
		this.requestor = requestor;
	}
	
	public String getRequestor() {
		return requestor;
	}
	
	public String getTarget() {
		return target;
	}
	
}