package com.git.api.integration.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;


import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;



public class HttpConnectorImpl {

	  private static final Logger LOGGER = Logger.getLogger(HttpsConnectorImpl.class);
	 
	    public HttpResponse httpUrlConnections(HttpsConnectionRequest httpRequest,String accessToken,String filePath) throws Exception {
	    	HttpResponse response; 
			try {
				
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(httpRequest.getServiceUrl());
				httpPost.addHeader("Authorization", accessToken);
	        
				FileBody uploadFilePart=new FileBody(new File(filePath));
				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("demo", uploadFilePart);
			
				httpPost.setEntity(reqEntity);
                response = httpclient.execute(httpPost);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
			return response;
		}
	 
	 
	public HttpsConnectionResponse httpUrlConnection(HttpsConnectionRequest httpRequest, String requestText) throws Exception {
		HttpURLConnection httpCon = null;
		URL url = null;
		StringBuffer response = null;
		
		HttpsConnectionResponse connectionResponse = new HttpsConnectionResponse(); 
		try {
			url = new URL(httpRequest.getServiceUrl());

			httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setRequestMethod(httpRequest.getHttpmethodName());
			httpCon.setRequestProperty("Content-Type", "application/json");

			httpCon.setDoOutput(true);
			
			httpCon.setInstanceFollowRedirects(false);
			httpCon.setUseCaches(false);
			
			setHeaderPropertiesToHTTPConnection(httpRequest, requestText, httpCon);

			//httpCon.getOutputStream().write(requestText.getBytes());
			if (requestText != null) {
				DataOutputStream wr = null;
				try {
					wr = new DataOutputStream(httpCon.getOutputStream());
					wr.writeBytes(requestText);
				} catch (Exception e) {
					LOGGER.error(e);
					throw new Exception(e);
				} finally {
					if(wr != null) {
						wr.flush();
						wr.close();
					}
				}
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

			String inputLine = null;

			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			LOGGER.debug("Response -->" + response.toString());
			connectionResponse.setResponseData(response.toString());

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}

		
		return connectionResponse;
	}
	
	
	public HttpsConnectionResponse httpUrlConnections(HttpsConnectionRequest httpRequest) throws Exception {
		HttpURLConnection httpCon = null;
		URL url = null;
		StringBuffer response = null;
		
		HttpsConnectionResponse connectionResponse = new HttpsConnectionResponse(); 
		try {
			url = new URL(httpRequest.getServiceUrl());

			
			httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setRequestMethod(httpRequest.getHttpmethodName());
			httpCon.setRequestProperty("Content-Type", "application/json");

			httpCon.setDoOutput(true);
			
			httpCon.setInstanceFollowRedirects(false);
			httpCon.setUseCaches(false);
			
			setHeaderPropertiesToHTTPConnection(httpRequest, httpCon);

			//httpCon.getOutputStream().write(requestText.getBytes());
			

			BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

			String inputLine = null;

			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			LOGGER.debug("Response -->" + response.toString());
			connectionResponse.setResponseData(response.toString());

		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}

		
		return connectionResponse;
	}
	
	
	/**
     * Set Header Properties To HTTPS Connection
     * @param httpsConnectionRequest
     * @param connectionData
     * @param httpsConnection
     */
    private void setHeaderPropertiesToHTTPConnection(
            final HttpsConnectionRequest httpsConnectionRequest, String connectionData,
            HttpURLConnection httpURLConnection) {

        setHeadersToHTTPConnection(httpsConnectionRequest.getHeaders(),
        		httpURLConnection);

        if (connectionData != null && connectionData.length() > 0) {

        	httpURLConnection.setRequestProperty("Content-Length",
                    String.valueOf(connectionData.length()));
        } else {
        	httpURLConnection.setRequestProperty("Content-Length", "0");
        }

    }
    private void setHeaderPropertiesToHTTPConnection(
            final HttpsConnectionRequest httpsConnectionRequest,
            HttpURLConnection httpURLConnection) {

        setHeadersToHTTPConnection(httpsConnectionRequest.getHeaders(),
        		httpURLConnection);

        
        	httpURLConnection.setRequestProperty("Content-Length", "0");
        }

    
    /**
     * Set headers that are required by calling app
     * @param headers
     * @param httpsConnection
     */
    private void setHeadersToHTTPConnection(Map<String, String> headers,
    		HttpURLConnection httpURLConnection) {

        if (headers != null && !headers.isEmpty() && httpURLConnection != null) {

            for (Entry<String, String> entry : headers.entrySet()) {

            	httpURLConnection.setRequestProperty(entry.getKey(),
                        entry.getValue());
            }
        }
    }
    
}
