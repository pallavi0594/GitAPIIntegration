/**
 * 
 */
package com.git.api.integration.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author - pallavi.mhetre
 *    file - GitProjectResponseDto.java
 *    date - 01-Dec-2019
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto {

	private List<GitProjectResponseDto> data;
	private ResponseStatus statusResponse;

	public ResponseStatus getStatusResponse() {
		return statusResponse;
	}

	public void setStatusResponse(ResponseStatus statusResponse) {
		this.statusResponse = statusResponse;
	}

	public List<GitProjectResponseDto> getData() {
		return data;
	}

	public void setData(List<GitProjectResponseDto> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CommonResponseDto [data=" + data + ", statusResponse=" + statusResponse + "]";
	}
}
