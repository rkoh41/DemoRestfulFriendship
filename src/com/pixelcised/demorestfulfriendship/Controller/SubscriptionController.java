package com.pixelcised.demorestfulfriendship.Controller;


import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import com.pixelcised.demorestfulfriendship.Model.SubscriptionManager;
import com.pixelcised.demorestfulfriendship.Model.Entity.Subscription;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.utils.ResponseUtil;

@Path("/subscription")
public class SubscriptionController {
	
	// demo requirement #4 requester subscribes to updates of 1 target email id
	@POST
	@Produces("application/json")
	@Path("/subscribe")
	public Response subscribe(@QueryParam("jsonString") String jsonString) {
		
		System.out.println("jsonString is " + jsonString);
		
		JsonObject jo = null;
		
		try {
			// parse urlencoded json string param
			JsonParser jp = new JsonParser();
			jo = (JsonObject)jp.parse(jsonString);
			
			// ensure exactly 2 email IDs are found in json object
			JsonElement jeRequestor = jo.get("requestor");
			JsonElement jeTarget = jo.get("target");
			if(jeRequestor == null || jeTarget == null) {
				JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1531, "Incorrect parameters");
				return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
			}
			String requestor = jeRequestor.getAsString();
			String target = jeTarget.getAsString();
			
			// invoke data manager using designated entity
			SubscriptionManager sm = new SubscriptionManager();
			boolean success = sm.addSubscription(new Subscription(requestor, target));
			
			if(success) {
				/*
				 * Richard pending issues
				 * I feel that if user conscientiously subcribes to another user, then any
				 * previous Blockage should be lifted. KIV since this business requirement is not clear.
				 * */
				
				return Response.status(Response.Status.OK).entity(ResponseUtil.createSuccessResponse().toString()).build();
			}
			else {
				JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1087, "Unable to subscribe to suggested email ID");
				return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
			}
		}
		catch (JsonParseException jse) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1023, "JSON Syntax Error");
			return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
		}
		catch (FriendshipException fe) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(2749, fe.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		catch (Exception e) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(9999, "Unknown Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		
	}
	
	// demo requirement #6 determine recipients of 1 sender email ids given message content
	@POST
	@Produces("application/json")
	@Path("/canreceiveupdate")
	public Response canreceiveupdate(@QueryParam("jsonString") String jsonString) {
		
		System.out.println("jsonString is " + jsonString);
		
		JsonObject jo = null;
		
		try {
			// parse urlencoded json string param
			JsonParser jp = new JsonParser();
			jo = (JsonObject)jp.parse(jsonString);
			
			// ensure exactly 2 fields are found in json object
			JsonElement jeSender = jo.get("sender");
			JsonElement jeText = jo.get("text");
			if(jeSender == null || jeText == null) {
				JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1532, "Incorrect parameters");
				return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
			}
			String sender = jeSender.getAsString();
			@SuppressWarnings("unused")
			String text = jeText.getAsString();
			
			// invoke data manager using sender value
			// retrieves list of userIDs who are subscribed to this userIDs
			SubscriptionManager sm = new SubscriptionManager();
			List<Subscription> subsList = sm.getSubscriberList(sender);
			
			JsonArray ja = new JsonArray();
			for ( int i = 0; i < subsList.size(); i++ ) {
				ja.add(subsList.get(i).getRequestor());
			}
			
			/*
			 * Richard pending item
			 * Ran out of time. Got to skip the logic for @mentions.
			 * 
			 * Here's what i would do:
			 * step 1: parse 'text' value for matching email regular expressions
			 * step 2: whatever email IDs found in the text, add into JsonArray ja
			 * 
			 * */
			
			// construct json response
			JsonObject responseObj = new JsonObject();
			responseObj.addProperty("success", true);
			responseObj.add("recipients", ja);
			
			return Response.status(Response.Status.OK).entity(responseObj.toString()).build();
		}
		catch (JsonParseException jse) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1023, "JSON Syntax Error");
			return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
		}
		catch (Exception e) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(9999, "Unknown Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		
	}
	
}
