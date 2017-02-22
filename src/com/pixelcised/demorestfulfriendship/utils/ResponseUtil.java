package com.pixelcised.demorestfulfriendship.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ResponseUtil {
	
	/*
	 * createStandardErrorResponse
	 * Provide fixed format json error response given internal error code and partner party-facing description
	 * */
	
	public static JsonObject createStandardErrorResponse(int code, String desc) {
		JsonObject responseObj = new JsonObject();
		JsonObject errorObj = new JsonObject();
		errorObj.addProperty("code", code);
		errorObj.addProperty("description", desc);
		responseObj.addProperty("success", false);
		responseObj.add("error", new Gson().toJsonTree(errorObj));
		return responseObj;
	}
	
	/*
	 * createSuccessResponse
	 * Helper for creating initial format json success response that always starts with success=true
	 * Note that some of our controllers may continue to add situation-specific data fields after "success"
	 * */
	
	public static JsonObject createSuccessResponse() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success", true);
		return responseObj;
	}

}
