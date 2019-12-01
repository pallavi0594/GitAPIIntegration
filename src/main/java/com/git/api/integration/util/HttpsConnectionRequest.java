package com.git.api.integration.util;

import java.util.Map;

public class HttpsConnectionRequest {

    private String serviceUrl;

    private String httpmethodName;

    private int port;

    private boolean doOutPut;

    private Map<String, String> headers;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getHttpmethodName() {
        return httpmethodName;
    }

    public void setHttpmethodName(String httpmethodName) {
        this.httpmethodName = httpmethodName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isDoOutPut() {
        return doOutPut;
    }

    public void setDoOutPut(boolean doOutPut) {
        this.doOutPut = doOutPut;
    }

}
