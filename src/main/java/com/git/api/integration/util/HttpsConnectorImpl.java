/**
 * 
 */
package com.git.api.integration.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;

public class HttpsConnectorImpl {

    private static final Logger LOGGER = Logger.getLogger(HttpsConnectorImpl.class);

    private static final int CHECK_RESPONSE_CODE = 200;

    /**
     * @param connectURL
     * @param data
     * @return
     * @throws UnknownHostException
     * @throws IOException
     * @throws ProtocolException
     * @throws MTAdapterConEx
     */
    public HttpsConnectionResponse connectionUsingHTTPS (String connectionData,
    		final HttpsConnectionRequest httpsConnectionRequest) throws UnknownHostException, IOException {
    	LOGGER.info("Start of connectionUsingHTTPS() in HttpsConnectorImpl");
    	SSLContext sslContext = null;
    	
    	HttpsConnectionResponse connectionResponse = new HttpsConnectionResponse();
    	try {
    		sslContext = SSLContext.getInstance("TLSv1.2");
    		sslContext
    		.init(null,
    				new javax.net.ssl.TrustManager[] { new TrustAllTrustManager() },
    				new java.security.SecureRandom());
    	} catch (Exception e) {
    		LOGGER.error("Exception occured while setting SSL factory", e);
    	}

    	URL httpsUrl = new URL(httpsConnectionRequest.getServiceUrl());

    	//sslContext.getSocketFactory().createSocket(httpsUrl.getHost(),httpsConnectionRequest.getPort());

    	HttpsURLConnection httpsConnection = (HttpsURLConnection) httpsUrl.openConnection();
    	LOGGER.info("Setting SSL Socket Factory...");

    	httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());

    	httpsConnection.setRequestMethod(httpsConnectionRequest.getHttpmethodName());
    	
    	//Read timeout Changed to 60 sec
    	httpsConnection.setReadTimeout(60000);

    	// Send post request
    	httpsConnection.setDoOutput(true);
    	httpsConnection.setDoInput(true);

    	setHeaderPropertiesToHTTPSConnection(httpsConnectionRequest, connectionData,
    			httpsConnection);

    	if (connectionData != null) {
    		LOGGER.info("connectionData length :: "+connectionData.length());
    		DataOutputStream wr = new DataOutputStream(
    				httpsConnection.getOutputStream());

    		wr.writeBytes(connectionData);
    		wr.flush();
    		wr.close();
    	}
    	int responseCode = httpsConnection.getResponseCode();

    	LOGGER.info("Response Status from connecting partner -->" + responseCode);

    	BufferedReader httpsResponse = null;

    	if (responseCode == CHECK_RESPONSE_CODE) {

    		httpsResponse = new BufferedReader(new InputStreamReader(
    				httpsConnection.getInputStream()));

    	} else if (responseCode == 204) {
    		//In case of 204 need to wait for 30sec and send request again
    		wait(30);
    		//Retry again and set BufferedReader and response string finally 
    		httpsResponse = connectionUsingHTTPSRetry(connectionData, httpsConnectionRequest);

    	} else {

    		if(httpsConnection != null) {
    			httpsResponse = new BufferedReader(new InputStreamReader(
    					httpsConnection.getErrorStream()));
    		}

    	}
    	
    	String output = null;
    	if(httpsResponse != null) {
    		output = readConnectionDataWithBuffer(httpsResponse);
    	}

    	if (httpsResponse != null) {
    		httpsResponse.close();
    	}

    	LOGGER.debug("Response -->" + output);
    	connectionResponse.setResponseData(output);

    	LOGGER.info("End of connectionUsingHTTPS() in HttpsConnectorImpl");
    	return connectionResponse;
    }
    
    
    /**
     * @param connectURL
     * @param data
     * @return
     * @throws UnknownHostException
     * @throws IOException
     * @throws ProtocolException
     * @throws MTAdapterConEx
     */
    private BufferedReader connectionUsingHTTPSRetry (String connectionData,
            final HttpsConnectionRequest httpsConnectionRequest) throws UnknownHostException, IOException {
        LOGGER.info("Start of connectionUsingHTTPSRetry() in HttpsConnectorImpl");
        SSLContext sslContext = null;

        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext
                    .init(null,
                            new javax.net.ssl.TrustManager[] { new TrustAllTrustManager() },
                            new java.security.SecureRandom());
        } catch (Exception e) {
            LOGGER.error("Exception occured while setting SSL factory", e);
        }

        URL httpsUrl = new URL(httpsConnectionRequest.getServiceUrl());

        sslContext.getSocketFactory().createSocket(httpsUrl.getHost(),
        		httpsConnectionRequest.getPort());

        HttpsURLConnection httpsConnection = (HttpsURLConnection) httpsUrl.openConnection();

        LOGGER.info("Setting SSL Socket Factory...");

        httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        httpsConnection.setRequestMethod(httpsConnectionRequest.getHttpmethodName());

        //Read timeout Changed to 60 sec
    	httpsConnection.setReadTimeout(60000);
    	
        // Send post request
        httpsConnection.setDoOutput(true);
        httpsConnection.setDoInput(true);

        setHeaderPropertiesToHTTPSConnection(httpsConnectionRequest, connectionData,
                httpsConnection);

        if (connectionData != null) {

            DataOutputStream wr = new DataOutputStream(
                    httpsConnection.getOutputStream());

            wr.writeBytes(connectionData);
            wr.flush();
            wr.close();
        }
        int responseCode = httpsConnection.getResponseCode();

        LOGGER.info("Response Status from connecting partner -->" + responseCode);

        BufferedReader httpsResponse = null;

        if (responseCode == CHECK_RESPONSE_CODE) {

            httpsResponse = new BufferedReader(new InputStreamReader(
                    httpsConnection.getInputStream()));

        } else {

            httpsResponse = new BufferedReader(new InputStreamReader(
                    httpsConnection.getErrorStream()));

        }
        
        String output = readConnectionDataWithBuffer(httpsResponse);
    	
    	if(output == null || output.isEmpty()) {
    		if(responseCode == CHECK_RESPONSE_CODE) {
    			output = "OK";
    		}
    	}

        LOGGER.info("End of connectionUsingHTTPSRetry() in HttpsConnectorImpl");
        return httpsResponse;
    }

    public static void wait(int seconds) {
        try {
            LOGGER.info("Waiting " + seconds + " seconds ..");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("Exception Occured in HttpsConnectorImpl :: "+e);
        }        
    }

    /**
     * @param httpsResponse
     * @return
     * @throws IOException
     */
    private String readConnectionDataWithBuffer(BufferedReader httpsResponse)
            throws IOException {
        String output;
        String responseLine;
        StringBuffer response = new StringBuffer();

        while ((responseLine = httpsResponse.readLine()) != null) {
            response.append(responseLine);
        }

        output = response.toString();
        return output;
    }
    
    /**
     * Set Header Properties To HTTPS Connection
     * @param httpsConnectionRequest
     * @param connectionData
     * @param httpsConnection
     */
    private void setHeaderPropertiesToHTTPSConnection(
            final HttpsConnectionRequest httpsConnectionRequest, String connectionData,
            HttpsURLConnection httpsConnection) {

        setHeadersToHTTPSConnection(httpsConnectionRequest.getHeaders(),
                httpsConnection);

        if (connectionData != null && connectionData.length() > 0) {

            httpsConnection.setRequestProperty("Content-Length",
                    String.valueOf(connectionData.length()));
        } else {
        	httpsConnection.setRequestProperty("Content-Length", "0");
        }

    }

    /**
     * Set headers that are required by calling app
     * @param headers
     * @param httpsConnection
     */
    private void setHeadersToHTTPSConnection(Map<String, String> headers,
            HttpsURLConnection httpsConnection) {

        if (headers != null && !headers.isEmpty() && httpsConnection != null) {

            for (Entry<String, String> entry : headers.entrySet()) {

                httpsConnection.setRequestProperty(entry.getKey(),
                        entry.getValue());
            }
        }
    }

}