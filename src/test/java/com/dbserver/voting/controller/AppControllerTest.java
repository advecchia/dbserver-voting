package com.dbserver.voting.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
public class AppControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@Before
    public void setup() throws Exception {
		
	}

	@Test
	public void shouldLoadHomePage() throws Exception {
    	this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string(containsString("DB Server Lunch Voting System")));
	}

	@Test
	public void shouldLoadUsingPartialHandler() throws Exception {
    	String page = "login";
		this.mockMvc.perform(get("/partials/{page}", page)).andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string(containsString("Login")));
	}
}
