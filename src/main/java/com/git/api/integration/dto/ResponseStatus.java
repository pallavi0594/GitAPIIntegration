/**
 * 
 */
package com.git.api.integration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author - pallavi.mhetre
 *    file - ResponseStatus.java
 *    date - 01-Dec-2019
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatus {

	private String statusCode;
	private String statusMessage;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Status [StatusCode=");
		builder.append(statusCode);
		builder.append(", statusMessage=");
		builder.append(statusMessage);
		builder.append("]");
		return builder.toString();
	}

}
