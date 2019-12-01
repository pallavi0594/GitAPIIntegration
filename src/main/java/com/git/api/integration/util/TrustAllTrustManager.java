package com.git.api.integration.util;

import org.apache.log4j.Logger;

public class TrustAllTrustManager implements javax.net.ssl.X509TrustManager {

	 private static final Logger LOGGER = Logger.getLogger(TrustAllTrustManager.class);

    public java.security.cert.X509Certificate[] getAcceptedIssuers() {

        return new java.security.cert.X509Certificate[] {};

    }

    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
            String authType) {
        LOGGER.info("Start checkClientTrusted() of TrustAllTrustManager");
        LOGGER.info("trust certificate"+certs);
        LOGGER.info("auth type"+authType);
        LOGGER.info("End checkClientTrusted() of TrustAllTrustManager");
    }

    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
            String authType) {
        LOGGER.info("Start checkServerTrusted() of TrustAllTrustManager");
        for (int index = 0; certs != null && index < certs.length; index++) {
            LOGGER.debug("Certificate [" + index + "]: "
                    + certs[0].getSubjectDN().getName());
        }
        LOGGER.info("auth type"+authType);
        LOGGER.info("End checkServerTrusted() of TrustAllTrustManager");
    }

}