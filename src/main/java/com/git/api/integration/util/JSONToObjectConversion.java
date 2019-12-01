package com.git.api.integration.util;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class JSONToObjectConversion {

	private static final Logger LOGGER = Logger.getLogger(JSONToObjectConversion.class);

	public static Object getObjectFromJson(String json, Class<?> responseClass) {
		LOGGER.debug("JSONToObjectConversion for String => " + json);
		Gson gson = new Gson();
		Object obj = gson.fromJson(json, responseClass);
		return obj;
	}
}
