/**
 * 
 */
package com.git.api.integration.dto;

/**
 * @author - pallavi.mhetre
 *    file - GithubResponseDto.java
 *    date - 01-Dec-2019
 */
public class GitProjectResponseDto {

	private String platform;
	private Object projectDetails;
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Object getProjectDetails() {
		return projectDetails;
	}
	public void setProjectDetails(Object projectDetails) {
		this.projectDetails = projectDetails;
	}
	@Override
	public String toString() {
		return "CommonResponseDto [platform=" + platform + ", projectDetails=" + projectDetails + "]";
	}
	
	
	
	
}
