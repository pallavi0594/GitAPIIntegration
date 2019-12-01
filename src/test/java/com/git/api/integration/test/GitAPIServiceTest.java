/**
 * 
 */
package com.git.api.integration.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.git.api.integration.dto.CommonResponseDto;
import com.git.api.integration.service.GitAPIService;

/**
 * @author - pallavi.mhetre
 *    file - GitAPIServiceTest.java
 *    date - 01-Dec-2019
 */

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringRunner.class)
public class GitAPIServiceTest {

	@Autowired
	GitAPIService repoService;
	
	@Test
	public void testGetProjects()
	{
		String username = "sudamb92";
		String type = "owner";
		
		CommonResponseDto response = (CommonResponseDto) repoService.getProjects(username, type);
		
		Assert.assertNotNull(response);
	}
}
