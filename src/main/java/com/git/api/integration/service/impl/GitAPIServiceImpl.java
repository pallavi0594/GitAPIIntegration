/**
 * 
 */
package com.git.api.integration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.git.api.integration.dto.CommonResponseDto;
import com.git.api.integration.dto.GitProjectResponseDto;
import com.git.api.integration.dto.ResponseStatus;
import com.git.api.integration.exception.ServiceResponseException;
import com.git.api.integration.service.GitAPIService;
import com.git.api.integration.util.CallServices;
import com.git.api.integration.util.CommonConstant;
import com.git.api.integration.util.JSONToObjectConversion;
/**
 * @author - pallavi.mhetre 
 * file - GitAPIServiceImpl.java 
 * date - 30-Nov-2019
 */

@Service("GitAPIService")
public class GitAPIServiceImpl implements GitAPIService {
	
	private Object getGithubRepos(String userName, String  type) throws ServiceResponseException {
		  String githubReposResp = "";
		  Object githubResponseObject = null;
		 
		  // filter github repos based on type like all, owner, member ( default:owner )
		  String githubURL = "https://api.github.com/users/" + userName + "/repos?type=" + type;
		 
		  try {
			  githubReposResp = CallServices.getResponseFromApi(githubURL,CommonConstant.ACCEPT_HEADER);
			  
			  githubResponseObject = JSONToObjectConversion.getObjectFromJson(githubReposResp,Object.class);
		  } catch (Exception e) {
			  
			  // set custom code and message for exception
			  ResponseStatus statusResponse = new ResponseStatus();
			  statusResponse.setStatusCode(CommonConstant.ERROR_CODE);
			  statusResponse.setStatusMessage(CommonConstant.GITHUB_ERROR_MESSAGE + " : " + e.getMessage());
			  
			  throw new ServiceResponseException(statusResponse);
		  }

		return githubResponseObject;
	}

	private Object getGitlabProjects(String userName, String type) throws ServiceResponseException{
		  String gitlabProjectsResp = "";
		  Object gitlabResponseObject  = null;
		 
		  // filter gitlab projects based on type like all, owner, member ( default: owner )
		  String gitlabURL = "https://gitlab.com/api/v4/users/" + userName + "/projects?type=" + type;
		  
		  try {
			  gitlabProjectsResp = CallServices.getResponseFromApi(gitlabURL, CommonConstant.ACCEPT_HEADER);

			  gitlabResponseObject = JSONToObjectConversion.getObjectFromJson(gitlabProjectsResp,
					  Object.class);
		  } catch (Exception e) {
			// set custom code and message for exception
			  ResponseStatus statusResponse = new ResponseStatus();
			  statusResponse.setStatusCode(CommonConstant.ERROR_CODE);
			  statusResponse.setStatusMessage(CommonConstant.GITLAB_ERROR_MESSAGE + " : " + e.getMessage());
			  
			  throw new ServiceResponseException(statusResponse);
		   }

		  return gitlabResponseObject;
	}

	public CommonResponseDto getProjects(String userName, String type) {
		CommonResponseDto projects = null;
		GitProjectResponseDto githubReposResponse = new GitProjectResponseDto();
		GitProjectResponseDto gitlabProjectResponse = new GitProjectResponseDto();
		List<GitProjectResponseDto> list = new ArrayList<GitProjectResponseDto>();
		
		Object gitHubRepos = null;
		Object gitlabProjects = null;
		
		try {
			
			gitHubRepos = this.getGithubRepos(userName, type);
			gitlabProjects = this.getGitlabProjects(userName, type);
			
			// set github response
			githubReposResponse.setPlatform(CommonConstant.GITHUB);
			githubReposResponse.setProjectDetails(gitHubRepos);
			
			// set gitlab response
			gitlabProjectResponse.setPlatform(CommonConstant.GITLAB);
			gitlabProjectResponse.setProjectDetails(gitlabProjects);

			// combine github and gitlab response data
			list.add(githubReposResponse);
			list.add(gitlabProjectResponse);
			
			projects = new CommonResponseDto();
			projects.setData(list);

		} catch (ServiceResponseException e) {
			ResponseStatus status = e.getStatus();
			projects = new CommonResponseDto();
			projects.setStatusResponse(status);
		}
		
		// return github and gitlab projects response data
		return projects;
	}
}
