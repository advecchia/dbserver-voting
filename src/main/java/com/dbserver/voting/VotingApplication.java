package com.dbserver.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dbserver.voting.configuration.JpaConfiguration;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.dbserver.voting"})
@EnableScheduling
public class VotingApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(VotingApplication.class, args);
	}
}
