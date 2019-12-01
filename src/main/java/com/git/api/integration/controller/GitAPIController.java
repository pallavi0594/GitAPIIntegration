/**
 * 
 */
package com.git.api.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.api.integration.dto.CommonResponseDto;
import com.git.api.integration.service.GitAPIService;
import com.git.api.integration.util.CommonConstant;

/**
 * @author - pallavi.mhetre
 *    file - GitAPIController.java
 *    date - 01-Dec-2019
 */
@Controller
public class GitAPIController {

	@Autowired
	GitAPIService gitAPIService;
		
	@ResponseBody
	@RequestMapping(value = "api/user/getProjects/{username}", method = RequestMethod.GET)
	public CommonResponseDto getUserRepos(@PathVariable String username, @RequestParam(required = false) String type)
	{
		if(type == null || type == "") {
			type = CommonConstant.TYPE_OWNER;
		}
		
		// get github and gitlab projects
		CommonResponseDto projectsResponse = (CommonResponseDto) gitAPIService.getProjects(username, type);
		return projectsResponse;
	}
}
