package com.dbserver.voting.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dbserver.voting.models.Restaurant;
import com.dbserver.voting.models.User;
import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.Winner;
import com.dbserver.voting.services.RestaurantService;
import com.dbserver.voting.services.UserService;
import com.dbserver.voting.services.VoteService;
import com.dbserver.voting.services.WinnerService;
import com.dbserver.voting.util.VotingErrorType;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	private static final String usersPath = "/users";
	private static final String restaurantsPath = "/restaurants";
	private static final String votesPath = "/votes";
	private static final String winnersPath = "/winners";

	@Autowired
    UserService userService;
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	VoteService voteService;
	
	@Autowired
	WinnerService winnerService;
	
	/**
	 * API for users
	 */
	// -------------------Authorize user by login-------------------------------------------

 	@RequestMapping(value = usersPath + "/authorize", method = RequestMethod.POST)
 	public ResponseEntity<?> authorizeUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
 		logger.info("Trying to authorize User : {}", user);

 		User existingUser = userService.authorize(user); 
 		logger.info("ExistingUser : {}", existingUser);
		if (existingUser == null) {
 			String message = "User or password invalid.";
 			logger.error(message);
 			return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
 		}
		existingUser.setPassword("");

 		return new ResponseEntity<User>(existingUser, HttpStatus.OK);
 	}

	// -------------------Retrieve All Users---------------------------------------------
	 
    @RequestMapping(value = usersPath + "/", method = RequestMethod.GET)
    private ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // -------------------Retrieve Single User------------------------------------------
    
    @RequestMapping(value = usersPath + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
        	String message = "User with id " + id + " not found";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // -------------------Create a User-------------------------------------------

 	@RequestMapping(value = usersPath + "/", method = RequestMethod.POST)
 	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
 		logger.info("Creating User : {}", user);

 		if (userService.isUserExist(user)) {
 			String message = "Unable to create. A User with username " + user.getUsername() + " already exist.";
 			logger.error(message);
 			return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.CONFLICT);
 		}
 		userService.saveUser(user);

 		HttpHeaders headers = new HttpHeaders();
 		headers.setLocation(ucBuilder.path("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri());
 		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
 	}

    // ------------------- Update a User ------------------------------------------------
    
    @RequestMapping(value = usersPath + "/{id}", method = RequestMethod.PUT)
    private ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        User currentUser = userService.findById(id);
 
        if (currentUser == null) {
        	String message = "Unable to update. User with id " + id + " not found.";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
 
        currentUser.setName(user.getName());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
    // ------------------- Delete a User-----------------------------------------
    
    @RequestMapping(value = usersPath + "/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting User with id {}", id);
 
        User user = userService.findById(id);
        if (user == null) {
            String message = "Unable to delete. User with id " + id + " not found.";
        	logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Users-----------------------------

    @RequestMapping(value = usersPath + "/", method = RequestMethod.DELETE)
    private ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

	/**
	 * API for restaurants
	 */

    // -------------------Retrieve All Restaurants---------------------------------------------
	 
    @RequestMapping(value = restaurantsPath + "/", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> listAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        if (restaurants.isEmpty()) {
            return new ResponseEntity<List<Restaurant>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
    }

    // -------------------Retrieve Single Restaurant------------------------------------------
    
    @RequestMapping(value = restaurantsPath + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRestaurant(@PathVariable("id") String id) {
        logger.info("Fetching Restaurant with id {}", id);
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant == null) {
        	String message = "Restaurant with id " + id + " not found";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }

    // -------------------Create a Restaurant-------------------------------------------

 	@RequestMapping(value = restaurantsPath + "/", method = RequestMethod.POST)
 	public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant, UriComponentsBuilder ucBuilder) {
 		logger.info("Creating Restaurant : {}", restaurant);

 		if (restaurantService.isRestaurantExist(restaurant)) {
 			logger.error("Unable to create. A Restaurant with name {} already exist", restaurant.getName());
 			return new ResponseEntity<>(new VotingErrorType("Unable to create. A Restaurant with name " + 
 					restaurant.getName() + " already exist."), HttpStatus.CONFLICT);
 		}
 		restaurantService.saveRestaurant(restaurant);

 		HttpHeaders headers = new HttpHeaders();
 		headers.setLocation(ucBuilder.path("/api/v1/restaurants/{id}").buildAndExpand(restaurant.getId()).toUri());
 		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
 	}

    // ------------------- Update a Restaurant ------------------------------------------------
    
    @RequestMapping(value = restaurantsPath + "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRestaurant(@PathVariable("id") String id, @RequestBody Restaurant restaurant) {
        logger.info("Updating Restaurant with id {}", id);
 
        Restaurant currentRestaurant = restaurantService.findById(id);
 
        if (currentRestaurant == null) {
        	String message = "Unable to update. Restaurant with id " + id + " not found.";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
 
        currentRestaurant.setName(restaurant.getName());

        restaurantService.updateRestaurant(currentRestaurant);
        return new ResponseEntity<Restaurant>(currentRestaurant, HttpStatus.OK);
    }
    
    // ------------------- Delete a Restaurant-----------------------------------------
    
    @RequestMapping(value = restaurantsPath + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRestaurant(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Restaurant with id {}", id);
 
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant == null) {
            String message = "Unable to delete. Restaurant with id " + id + " not found.";
        	logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }

        try {
        	restaurantService.deleteRestaurantById(id);
        	return new ResponseEntity<Restaurant>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	String message = "Unable to delete a Restaurant that has votes.";
        	logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_MODIFIED);
        }
    }

    // ------------------- Delete All Restaurants-----------------------------

    @RequestMapping(value = restaurantsPath + "/", method = RequestMethod.DELETE)
    private ResponseEntity<Restaurant> deleteAllRestaurants() {
        logger.info("Deleting All Restaurants");
 
        restaurantService.deleteAllRestaurants();
        return new ResponseEntity<Restaurant>(HttpStatus.NO_CONTENT);
    }

	/**
	 * API for winners
	 */

    // -------------------Retrieve All Winners---------------------------------------------
	 
    @RequestMapping(value = winnersPath + "/", method = RequestMethod.GET)
    public ResponseEntity<List<Winner>> listAllWinners() {
        List<Winner> winners = winnerService.findAllWinners();
        if (winners.isEmpty()) {
            return new ResponseEntity<List<Winner>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Winner>>(winners, HttpStatus.OK);
    }

    // -------------------Retrieve Single Winner------------------------------------------
    
    @RequestMapping(value = winnersPath + "/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> getWinner(@PathVariable("id") String id) {
        logger.info("Fetching Winner with id {}", id);
        Winner winner = winnerService.findById(id);
        if (winner == null) {
        	String message = "Winner with id " + id + " not found";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Winner>(winner, HttpStatus.OK);
    }

    // -------------------Create a Winner-------------------------------------------

 	@RequestMapping(value = winnersPath + "/", method = RequestMethod.POST)
 	private ResponseEntity<?> createWinner(@RequestBody Winner winner, UriComponentsBuilder ucBuilder) {
 		logger.info("Creating Winner : {}", winner);

 		if (winnerService.isWinnerExist(winner)) {
 			String message = "Unable to create. A Winner for date " + winner.getLunchDate() + " already exist.";
 			logger.error(message);
 			return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.CONFLICT);
 		}
 		winnerService.saveWinner(winner);

 		HttpHeaders headers = new HttpHeaders();
 		headers.setLocation(ucBuilder.path("/api/v1/winners/{id}").buildAndExpand(winner.getRestaurant().getId()).toUri());
 		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
 	}

    // ------------------- Update a Winner ------------------------------------------------
    
    @RequestMapping(value = winnersPath + "/{id}", method = RequestMethod.PUT)
    private ResponseEntity<?> updateWinner(@PathVariable("id") String id, @RequestBody Winner winner) {
        logger.info("Updating Winner with id {}", id);
 
        Winner currentWinner = winnerService.findById(id);
 
        if (currentWinner == null) {
        	String message = "Unable to update. Winner with id " + id + " not found.";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
 
        currentWinner.setLunchDate(winner.getLunchDate());

        winnerService.updateWinner(currentWinner);
        return new ResponseEntity<Winner>(currentWinner, HttpStatus.OK);
    }
    
    // ------------------- Delete a Winner-----------------------------------------
    
    @RequestMapping(value = winnersPath + "/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteWinner(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Winner with id {}", id);
 
        Winner winner = winnerService.findById(id);
        if (winner == null) {
            String message = "Unable to delete. Winner with id " + id + " not found.";
        	logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
        winnerService.deleteWinnerById(id);
        return new ResponseEntity<Winner>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Winners-----------------------------

    @RequestMapping(value = winnersPath + "/", method = RequestMethod.DELETE)
    private ResponseEntity<Winner> deleteAllWinners() {
        logger.info("Deleting All Winners");
 
        winnerService.deleteAllWinners();
        return new ResponseEntity<Winner>(HttpStatus.NO_CONTENT);
    }

	/**
	 * API for votes
	 */

    // -------------------Retrieve All Votes---------------------------------------------
	 
    @RequestMapping(value = votesPath + "/", method = RequestMethod.GET)
    public ResponseEntity<List<Vote>> listAllVotes() {
        List<Vote> votes = voteService.findAllVotes();
        if (votes.isEmpty()) {
            return new ResponseEntity<List<Vote>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Vote>>(votes, HttpStatus.OK);
    }
    
    // -------------------Retrieve Vote by Id ------------------------------------------
    
    @RequestMapping(value = votesPath + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Vote> getVoteById(@PathVariable("id") String id) {
        logger.info("Fetching Vote with id {}", id);
        Vote vote = voteService.findById(id);
        if (vote != null) {
        	String message = "Vote with id " + id + " not found";
            logger.error(message);
            return new ResponseEntity<Vote>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Vote>(vote, HttpStatus.OK);
    }

    // -------------------Retrieve Votes by User ------------------------------------------
    
    @RequestMapping(value = votesPath + "/user/{id}", method = RequestMethod.GET)
    private ResponseEntity<List<Vote>> getVotesByUser(@PathVariable("id") String id) {
        logger.info("Fetching Votes with userId {}", id);
        List<Vote> votes = voteService.findByUserId(id);
        if (votes.isEmpty()) {
        	String message = "Votes with userId " + id + " not found";
            logger.error(message);
            return new ResponseEntity<List<Vote>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Vote>>(votes, HttpStatus.OK);
    }

    // -------------------Retrieve Votes by Restaurant ------------------------------------------
    
    @RequestMapping(value = votesPath + "/restaurant/{id}", method = RequestMethod.GET)
    private ResponseEntity<List<Vote>> getVotesByRestaurant(@PathVariable("id") String id) {
        logger.info("Fetching Votes with restaurantId {}", id);
        List<Vote> votes = voteService.findByRestaurantId(id);
        if (votes.isEmpty()) {
        	String message = "Votes with restaurantId " + id + " not found";
            logger.error(message);
            return new ResponseEntity<List<Vote>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Vote>>(votes, HttpStatus.OK);
    }

    // -------------------Retrieve Votes by Voting Date ------------------------------------------
    
    @RequestMapping(value = votesPath + "/date/{votingDate}", method = RequestMethod.GET)
    private ResponseEntity<List<Vote>> getVotesByVotingDate(@PathVariable("votingDate") Date votingDate) {
        logger.info("Fetching Votes with votingDate {}", votingDate);
        List<Vote> votes = voteService.findByVotingDate(votingDate);
        if (votes.isEmpty()) {
        	String message = "Votes with votingDate " + votingDate + " not found";
            logger.error(message);
            return new ResponseEntity<List<Vote>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Vote>>(votes, HttpStatus.OK);
    }

    // -------------------Create a Vote-------------------------------------------

 	@RequestMapping(value = votesPath + "/", method = RequestMethod.POST)
 	public ResponseEntity<?> createVote(@RequestBody Vote vote, UriComponentsBuilder ucBuilder) {
 		logger.info("Creating Vote : {}", vote);

 		if (voteService.isVoteExist(vote)) {
 			String message = "You already have voted today.";
 			logger.error(message);
 			return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.CONFLICT);
 		}
 		voteService.saveVote(vote);

 		HttpHeaders headers = new HttpHeaders();
 		headers.setLocation(ucBuilder.path("/api/v1/votes/{id}").buildAndExpand(vote.getId()).toUri());
 		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
 	}

    // ------------------- Update a Vote ------------------------------------------------
    
 	@RequestMapping(value = votesPath + "/{id}", method = RequestMethod.PUT)
    private ResponseEntity<?> updateVote(@PathVariable("id") String id, @RequestBody Vote vote) {
        logger.info("Updating Vote with id {}", id);

        Vote currentVote = voteService.findById(id);
 
        if (currentVote == null) {
        	String message = "Unable to update. A Vote for id " + id + " not exist.";
            logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
 
        currentVote.setId(id);
        currentVote.setUser(vote.getUser());
        currentVote.setRestaurant(vote.getRestaurant());

        voteService.updateVote(currentVote);
        return new ResponseEntity<Vote>(currentVote, HttpStatus.OK);
    }
    
    // ------------------- Delete a Vote-----------------------------------------
    
 	@RequestMapping(value = votesPath + "/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deleteVote(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Vote with id {}", id);
 
        Vote vote = voteService.findById(id);
        if (vote == null) {
        	String message = "Unable to delete. A Vote for id " + id + " not found.";
        	logger.error(message);
            return new ResponseEntity<>(new VotingErrorType(message), HttpStatus.NOT_FOUND);
        }
        voteService.deleteVoteById(id);
        return new ResponseEntity<Vote>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Votes-----------------------------

    @RequestMapping(value = votesPath + "/", method = RequestMethod.DELETE)
    private ResponseEntity<Vote> deleteAllVotes() {
        logger.info("Deleting All Votes");
 
        voteService.deleteAllVotes();
        return new ResponseEntity<Vote>(HttpStatus.NO_CONTENT);
    }
}
