package com.pixelcised.demorestfulfriendship.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import com.pixelcised.demorestfulfriendship.Model.BlockageManager;
import com.pixelcised.demorestfulfriendship.Model.FriendConnectionManager;
import com.pixelcised.demorestfulfriendship.Model.SubscriptionManager;
import com.pixelcised.demorestfulfriendship.Model.Entity.Blockage;
import com.pixelcised.demorestfulfriendship.Model.Entity.FriendConnection;
import com.pixelcised.demorestfulfriendship.Model.Entity.Subscription;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.utils.ResponseUtil;

@Path("/blockage")
public class BlockageController {
	
	// demo requirement #5 requestor blocks 1 email id
	@POST
	@Produces("application/json")
	@Path("/block")
	public Response block(@QueryParam("jsonString") String jsonString) {
		
		System.out.println("jsonString is " + jsonString);
		
		JsonObject jo = null;
		
		try {
			// parse urlencoded json string param
			JsonParser jp = new JsonParser();
			jo = (JsonObject)jp.parse(jsonString);
			
			// ensure both expected fields are found in json object
			JsonElement jeRequestor = jo.get("requestor");
			JsonElement jeTarget = jo.get("target");
			if(jeRequestor == null || jeTarget == null) {
				JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1531, "Incorrect parameters");
				return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
			}
			String requester = jeRequestor.getAsString();
			String target = jeTarget.getAsString();
			
			// invoke data manager using designated entity
			BlockageManager bm = new BlockageManager();
			Blockage bloc = new Blockage(requester, target);
			boolean success = bm.addBlockage(bloc);
			
			if(success) {
		    	// check whether friend connection exists between the two users
		    	FriendConnectionManager fcm = new FriendConnectionManager(); 
		    	FriendConnection fc = new FriendConnection(bloc.getRequestor(), bloc.getTarget());
		    	if(fcm.hasFriendConnection(fc)) {
			    	// Un-subscribe requestor from target's news if was subscribed.
			    	SubscriptionManager sm = new SubscriptionManager(); 
			    	Subscription subs = new Subscription(bloc.getRequestor(), bloc.getTarget());
			    	sm.removeSubscription(subs);
		    	}
				
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
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(2369, fe.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		catch (Exception e) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(9999, "Unknown Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		
	}

}
