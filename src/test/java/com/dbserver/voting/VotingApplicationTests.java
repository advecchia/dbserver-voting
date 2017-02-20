package com.dbserver.voting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.dbserver.voting.controller.RestApiController;

@RunWith(SpringRunner.class)
@SpringBootTest(
	    classes = VotingApplication.class,
	    webEnvironment = WebEnvironment.RANDOM_PORT
)
public class VotingApplicationTests {

	@Autowired
	RestApiController restApiController;

	@Test
	public void contextLoads() {
		assertThat(restApiController).isNotNull();
	}

}
