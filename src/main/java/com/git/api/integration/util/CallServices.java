package com.git.api.integration.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.git.api.integration.util.HttpConnectorImpl;
import com.git.api.integration.util.HttpsConnectionRequest;
import com.git.api.integration.util.HttpsConnectionResponse;


public class CallServices {
	
private static final Logger LOGGER = Logger.getLogger(CallServices.class);
	
	public static String getResponseFromApi(String url, String header) throws Exception {

		HttpsConnectionResponse httpsConResult = null;
		String str_result = null;
		
		LOGGER.info("In CallServices in getResponseFromApi Request Body =>");
		LOGGER.info("url="+url+ " " +"header= "+header);
		try {
			HttpsConnectionRequest connectionRequest = new HttpsConnectionRequest();
			
			Map<String, String> headerMap = new HashMap<String, String>();
			
			headerMap.put(CommonConstant.CONTENT_TYPE, CommonConstant.APPLICATION_JSON);
			if(header != null || header != "") {
				headerMap.put(CommonConstant.ACCEPT, header);				
			}

			LOGGER.debug("URL : "+url);
			connectionRequest.setServiceUrl(url);
			connectionRequest.setHeaders(headerMap);
			connectionRequest.setHttpmethodName("GET");
			
			boolean isHttps = true;
			if (isHttps) {
				connectionRequest.setPort(8443);
				HttpsConnectorImpl httpsConnectorImpl = new HttpsConnectorImpl();
				httpsConResult = httpsConnectorImpl.connectionUsingHTTPS(null, connectionRequest);
			} else {
				HttpConnectorImpl httpConnectorImpl = new HttpConnectorImpl();
				httpsConResult=httpConnectorImpl.httpUrlConnection(connectionRequest,null);
			}
			str_result=httpsConResult.getResponseData();
			
			LOGGER.info("In CallServices in getResponseFromService(url,request,accessToken) Response Body =>");
			LOGGER.info(str_result);
		} catch (Exception e) {			
			LOGGER.error("==>Exception thrown in CallServices in getResponseFromService(url,request,accessToken) : "+e);
			throw new Exception(e);
		}
		return str_result;
	}
	
}
