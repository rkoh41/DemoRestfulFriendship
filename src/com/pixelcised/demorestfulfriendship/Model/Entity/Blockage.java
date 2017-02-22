package com.pixelcised.demorestfulfriendship.Model.Entity;

public class Blockage {
	
	private String requestor;
	private String target;
	
	public Blockage() {
		
	}
	
	public Blockage(String requestor, String target) {
		this.requestor = requestor;
		this.target = target;
	}
	
	public String getRequestor() {
		return requestor;
	}
	
	public String getTarget() {
		return target;
	}
	
}
