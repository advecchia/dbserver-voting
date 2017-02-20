package com.dbserver.voting.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dbserver.voting.models.Restaurant;
import com.dbserver.voting.models.User;
import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.Winner;
import com.dbserver.voting.services.RestaurantService;
import com.dbserver.voting.services.UserService;
import com.dbserver.voting.services.VoteService;
import com.dbserver.voting.services.WinnerService;

@RunWith(SpringRunner.class)
@WebMvcTest(RestApiController.class)
public class RestApiControllerTest {
	/**
	 * Be aware that only public API methods are guaranteed tested. 
	 */

	private static final String basePath = "/api/v1";
	private static final String usersPath = basePath + "/users";
	private static final String restaurantsPath = basePath + "/restaurants";
	private static final String votesPath = basePath + "/votes";
	private static final String winnersPath = basePath + "/winners";
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private static final String mockId = UUID.randomUUID().toString();
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;
	
    @MockBean
	RestaurantService restaurantService;
	
    @MockBean
	VoteService voteService;
	
    @MockBean
	WinnerService winnerService;
    
    @Before
    public void setup() throws Exception {
    	String	uname = "uname", 
    			rname = "rname", 
    			username = "username", 
    			password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	User mockUser = new User(mockId, uname, username, password, votes);
    	userService.saveUser(mockUser);

    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant mockRestaurant = new Restaurant(mockId, rname, winners, votes);
    	restaurantService.saveRestaurant(mockRestaurant);
    	
    	Date mockLunchDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Winner mockWinner = new Winner(mockId, mockLunchDate, mockRestaurant);
    	winnerService.saveWinner(mockWinner);
    	
    	Date mockVotingDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Vote mockVote = new Vote(mockId, mockVotingDate, mockUser, mockRestaurant);
    	voteService.saveVote(mockVote);
    }

    /**
     * Users API
     */

    @Test
    public void shouldAuthorizeUser() throws Exception {
    	String	name = "name", 
    			username = "username", 
    			password = "password";
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(mockId, name, username, password, votes);
    	
    	when(userService.authorize(user)).thenReturn(validUser);
    	this.mockMvc.perform(post(usersPath + "/authorize").contentType(contentType).content(this.parseToJsonString(user)))
    		.andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string(containsString("username")));
    }
    
    @Test
    public void shouldFailToAuthorizeUser() throws Exception {
    	String	username = "username", 
    			password = "password";
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	User invalidUser = null;
    	
    	when(userService.authorize(user)).thenReturn(invalidUser);
    	this.mockMvc.perform(post(usersPath + "/authorize").contentType(contentType).content(this.parseToJsonString(user)))
    		.andDo(print()).andExpect(status().isNotFound())
    		.andExpect(content().string(containsString("User or password invalid.")));
    }

    /*@Test
    public void shouldRetrieveAllUsers() throws Exception {
    	String	id = "id", 
    			name = "name", 
    			username = "username", 
    			password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(id, name, username, password, votes);
    	List<User> validUsers = new ArrayList<>();
    	validUsers.add(validUser);
    	
    	when(userService.findAllUsers()).thenReturn(validUsers);
    	this.mockMvc.perform(get(usersPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string("username"));
    }

    @Test
    public void shouldFailToRetrieveAllUsers() throws Exception {
    	List<User> validUsers = new ArrayList<>();
    	
    	when(userService.findAllUsers()).thenReturn(validUsers);
    	this.mockMvc.perform(get(usersPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isNoContent())
    		.andExpect(content().string("username"));
    }*/

    @Test
    public void shouldRetrieveOneUser() throws Exception {
    	String	name = "name", 
    			username = "username", 
    			password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(mockId, name, username, password, votes);
    	
    	when(userService.findById(mockId)).thenReturn(validUser);
    	this.mockMvc.perform(get(usersPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldFailToRetrieveOneUser() throws Exception {
    	User invalidUser = null;
    	
    	when(userService.findById(mockId)).thenReturn(invalidUser);
    	this.mockMvc.perform(get(usersPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateOneUser() throws Exception {
    	String	name = "uname", 
    			username = "username2", 
    			password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(mockId, name, username, password, votes);
    	
    	when(userService.isUserExist(validUser)).thenReturn(false);
     	doNothing().when(userService).saveUser(validUser);
    	this.mockMvc.perform(post(usersPath + "/").contentType(contentType).content(parseToJsonString(validUser)))
    		.andDo(print()).andExpect(status().isCreated());
    }

    /*Make this test pass
     * @Test
    public void shouldFailToCreateOneUser() throws Exception {
    	String	name = "uname", 
    			username = "username", 
    			password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	User invalidUser = new User(mockId, name, username, password, votes);
    	
    	when(userService.isUserExist(invalidUser)).thenReturn(true);
    	this.mockMvc.perform(post(usersPath + "/").contentType(contentType).content(parseToJsonString(invalidUser)))
    		.andDo(print()).andExpect(status().isConflict());
    }*/
    
    /**
     * Restaurants API
     */
    @Test
    public void shouldRetrieveAllRestaurants() throws Exception {
    	String name = "rname";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(mockId, name, winners, votes);
    	List<Restaurant> validRestaurants = new ArrayList<>();
    	validRestaurants.add(validRestaurant);
    	
    	when(restaurantService.findAllRestaurants()).thenReturn(validRestaurants);
    	this.mockMvc.perform(get(restaurantsPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldFailToRetrieveAllRestaurants() throws Exception {
    	List<Restaurant> validRestaurants = new ArrayList<>();
    	
    	when(restaurantService.findAllRestaurants()).thenReturn(validRestaurants);
    	this.mockMvc.perform(get(restaurantsPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isNoContent());
    }
    
    @Test
    public void shouldRetrieveOneRestaurant() throws Exception {
    	String name = "rname";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(mockId, name, winners, votes);
    	
    	when(restaurantService.findById(mockId)).thenReturn(validRestaurant);
    	this.mockMvc.perform(get(restaurantsPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldFailToRetrieveOneRestaurant() throws Exception {
    	Restaurant invalidRestaurant = null;
    	
    	when(restaurantService.findById(mockId)).thenReturn(invalidRestaurant);
    	this.mockMvc.perform(get(restaurantsPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateOneRestaurant() throws Exception {
    	String name = "rname2";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(null, name, winners, votes);
    	
    	when(restaurantService.isRestaurantExist(validRestaurant)).thenReturn(false);
    	doNothing().when(restaurantService).saveRestaurant(validRestaurant);
    	this.mockMvc.perform(post(restaurantsPath + "/").contentType(contentType).content(parseToJsonString(validRestaurant)))
    		.andDo(print()).andExpect(status().isCreated());
    }

    /*Make this test pass
    	@Test
    public void shouldFailToCreateOneRestaurant() throws Exception {
    	String name = "rname";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant invalidRestaurant = new Restaurant(mockId, name, winners, votes);
    	
    	when(restaurantService.isRestaurantExist(invalidRestaurant)).thenReturn(true);
    	this.mockMvc.perform(post(restaurantsPath + "/").contentType(contentType).content(parseToJsonString(invalidRestaurant)))
    		.andDo(print()).andExpect(status().isConflict());
    }*/

    @Test
    public void shouldUpdateOneRestaurant() throws Exception {
    	String name = "rname2";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(mockId, name, winners, votes);
    	
    	when(restaurantService.findById(mockId)).thenReturn(validRestaurant);
    	doNothing().when(restaurantService).updateRestaurant(validRestaurant);
    	this.mockMvc.perform(put(restaurantsPath + "/{id}", mockId).contentType(contentType).content(parseToJsonString(validRestaurant)))
    		.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldFailToUpdateOneRestaurant() throws Exception {
    	String fakeId = UUID.randomUUID().toString();
    	String name = "rname2";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant invalidRestaurant = new Restaurant(fakeId, name, winners, votes);
    	
    	when(restaurantService.findById(fakeId)).thenReturn(null);
    	this.mockMvc.perform(put(restaurantsPath + "/{id}", fakeId).contentType(contentType).content(parseToJsonString(invalidRestaurant)))
    		.andDo(print()).andExpect(status().isNotFound());
    }
    
    @Test
    public void shouldDeleteOneRestaurant() throws Exception {
    	String name = "rname";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(mockId, name, winners, votes);
    	
    	when(restaurantService.findById(mockId)).thenReturn(validRestaurant);
    	doNothing().when(restaurantService).deleteRestaurantById(mockId);
    	this.mockMvc.perform(delete(restaurantsPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void shouldFailToDeleteOneRestaurant() throws Exception {
    	String fakeId = UUID.randomUUID().toString();
    	Restaurant invalidRestaurant = null;
    	
    	when(restaurantService.findById(fakeId)).thenReturn(invalidRestaurant);
    	this.mockMvc.perform(delete(restaurantsPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void shouldFailToDeleteOneRestaurantWithVotes() throws Exception {
    	String name = "rname";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(mockId, name, winners, votes);
    	
    	when(restaurantService.findById(mockId)).thenReturn(validRestaurant);
    	doThrow(Exception.class).when(restaurantService).deleteRestaurantById(mockId);
    	this.mockMvc.perform(delete(restaurantsPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isNotModified());
    }

    /**
     * Winners API
     */
    @Test
    public void shouldRetrieveAllWinners() throws Exception {
    	String name = "rname";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	Restaurant validRestaurant = new Restaurant(mockId, name, winners, votes);
    	Date mockLunchDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Winner validWinner = new Winner(mockId, mockLunchDate, validRestaurant);
    	List<Winner> validWinners = new ArrayList<>();
    	validWinners.add(validWinner);
    	
    	when(winnerService.findAllWinners()).thenReturn(validWinners);
		this.mockMvc.perform(get(winnersPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldFailToRetrieveAllWinners() throws Exception {
    	List<Winner> validWinners = new ArrayList<>();
    	
    	when(winnerService.findAllWinners()).thenReturn(validWinners);
    	this.mockMvc.perform(get(winnersPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isNoContent());
    }

    /**
     * Votes API
     */
    @Test
    public void shouldRetrieveAllVotes() throws Exception {
    	String uname = "uname", rname = "rname", 
    			username = "username", password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	User validUser = new User(mockId, uname, username, password, votes);
    	Restaurant validRestaurant = new Restaurant(mockId, rname, winners, votes);
    	Date mockVotingDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Vote validVote = new Vote(mockId, mockVotingDate, validUser, validRestaurant);
    	List<Vote> validVotes = new ArrayList<>();
    	validVotes.add(validVote);
    	
    	when(voteService.findAllVotes()).thenReturn(validVotes);
		this.mockMvc.perform(get(votesPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldFailToRetrieveAllVotes() throws Exception {
    	List<Vote> validVotes = new ArrayList<>();
    	
    	when(voteService.findAllVotes()).thenReturn(validVotes);
    	this.mockMvc.perform(get(votesPath + "/").contentType(contentType))
    		.andDo(print()).andExpect(status().isNoContent());
    }

    /** Make this test pass
    	@Test
    public void shouldRetrieveOneVote() throws Exception {
    	String uname = "uname", rname = "rname", 
    			username = "username", password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	User validUser = new User(mockId, uname, username, password, votes);
    	Restaurant validRestaurant = new Restaurant(mockId, rname, winners, votes);
    	Date mockVotingDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Vote validVote = new Vote(mockId, mockVotingDate, validUser, validRestaurant);
    	
    	when(voteService.findById(mockId)).thenReturn(validVote);
		this.mockMvc.perform(get(votesPath + "/{id}", mockId).contentType(contentType))
    		.andDo(print()).andExpect(status().isOk());
    }*/

    /** Make this test pass
    	@Test
    public void shouldFailToRetrieveOneVote() throws Exception {
    	String fakeId = UUID.randomUUID().toString();
    	Vote invalidVote = null;
    	
    	when(voteService.findById(fakeId)).thenReturn(invalidVote);
		this.mockMvc.perform(get(votesPath + "/{id}", fakeId).contentType(contentType))
    		.andDo(print()).andExpect(status().isNotFound());
    }*/
    
    @Test
    public void shouldCreateOneVote() throws Exception {
    	String uname = "uname", rname = "rname", 
    			username = "username", password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	User validUser = new User(mockId, uname, username, password, votes);
    	Restaurant validRestaurant = new Restaurant(mockId, rname, winners, votes);
    	Date mockVotingDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Vote validVote = new Vote(null, mockVotingDate, validUser, validRestaurant);
    	
    	when(voteService.isVoteExist(validVote)).thenReturn(false);
    	doNothing().when(voteService).saveVote(validVote);
		this.mockMvc.perform(post(votesPath + "/").contentType(contentType).content(parseToJsonString(validVote)))
    		.andDo(print()).andExpect(status().isCreated());
    }

    /*Make this test pass
    	@Test
    public void shouldFailToCreateOneVote() throws Exception {
    	String uname = "uname", rname = "rname", 
    			username = "username", password = "password";
    	Set<Vote> votes = new HashSet<Vote>();
    	Set<Winner> winners = new HashSet<Winner>();
    	User validUser = new User(mockId, uname, username, password, votes);
    	Restaurant validRestaurant = new Restaurant(mockId, rname, winners, votes);
    	Date mockVotingDate = new Date(Calendar.getInstance().getTimeInMillis());
    	Vote validVote = new Vote(mockId, mockVotingDate, validUser, validRestaurant);
    	
    	when(voteService.isVoteExist(validVote)).thenReturn(true);
		this.mockMvc.perform(post(votesPath + "/").contentType(contentType).content(parseToJsonString(validVote)))
    		.andDo(print()).andExpect(status().isConflict());
    }*/	

    /**
     * Helpers
     */

    protected String parseToJsonString(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
