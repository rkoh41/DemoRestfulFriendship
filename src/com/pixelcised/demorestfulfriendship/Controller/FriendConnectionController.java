package com.pixelcised.demorestfulfriendship.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;

import com.pixelcised.demorestfulfriendship.Model.Entity.FriendConnection;
import com.pixelcised.demorestfulfriendship.Model.FriendConnectionManager;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;
import com.pixelcised.demorestfulfriendship.utils.ResponseUtil;

@Path("/friendconnection")
public class FriendConnectionController {
	
	// demo requirement #1 create connection between 2 email ids
	@POST
	@Produces("application/json")
	@Path("/create")
	public Response create(@QueryParam("jsonString") String jsonString) {
		
		System.out.println("jsonString is " + jsonString);
		
		JsonObject jo = null;
		
		try {
			// parse urlencoded json string param
			JsonParser jp = new JsonParser();
			jo = (JsonObject)jp.parse(jsonString);
			
			// ensure length of "friends" json array is exactly 2
			JsonArray jeFriendsArray = jo.getAsJsonArray("friends");
			if(jeFriendsArray == null || jeFriendsArray.size() != 2) {
				JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1509, "Incorrect number of parameters");
				return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
			}
			else {
				String email1 = jeFriendsArray.get(0).getAsString();
				String email2 = jeFriendsArray.get(1).getAsString();
				
				FriendConnection fc1 = new FriendConnection(email1, email2);
				FriendConnection fc2 = new FriendConnection(email2, email1);
				
				// invoke data manager using designated entity
				FriendConnectionManager fcm = new FriendConnectionManager();
				boolean success1 = fcm.addFriendConnection(fc1);
				boolean success2 = fcm.addFriendConnection(fc2);
				
				if(success1 || success2) {
					return Response.status(Response.Status.OK).entity(ResponseUtil.createSuccessResponse().toString()).build();
				}
				else {
					JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1085, "Unable to create friend connection for suggested email IDs");
					return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
				}
			}
		}
		catch (JsonParseException jse) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(1023, "JSON Syntax Error");
			return Response.status(Response.Status.BAD_REQUEST).entity(responseObj.toString()).build();
		}
		catch (FriendshipException fe) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(2398, fe.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		catch (Exception e) {
			JsonObject responseObj = ResponseUtil.createStandardErrorResponse(9999, "Unknown Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
		}
		
	}
	
}
