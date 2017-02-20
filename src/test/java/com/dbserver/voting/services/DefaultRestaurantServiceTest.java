package com.dbserver.voting.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dbserver.voting.models.Restaurant;
import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.Winner;
import com.dbserver.voting.repositories.RestaurantRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(DefaultRestaurantService.class)
public class DefaultRestaurantServiceTest {

	private static final String mockId = UUID.randomUUID().toString();
	
	@Mock
	RestaurantRepository restaurantRepository;

	@InjectMocks
    RestaurantService restaurantService = new DefaultRestaurantService();
    
    @Mock
    Restaurant mockRestaurant;
    
    @Before
    public void setup() throws Exception {
    	String	rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	mockRestaurant = new Restaurant(mockId, rname, winners, votes);
    }
    
    @Test
    public void shouldFindbyId() throws Exception {
    	when(restaurantRepository.findOne(mockId)).thenReturn(mockRestaurant);
    	Restaurant restaurant = restaurantService.findById(mockId);
    	assertNotNull(restaurant);
    	assertEquals(restaurant, mockRestaurant);
    	verify(restaurantRepository).findOne(mockId);
    }
    
    @Test
    public void shouldFindbyName() throws Exception {
    	String name = "rname";
    	when(restaurantRepository.findByName(name)).thenReturn(mockRestaurant);
    	Restaurant restaurant = restaurantService.findByName(name);
    	assertNotNull(restaurant);
    	assertEquals(restaurant, mockRestaurant);
    	verify(restaurantRepository).findByName(name);
    }
    
    @Test
    public void shouldSaveRestaurant() throws Exception {
    	String uuid = UUID.randomUUID().toString();
    	String	rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Restaurant validRestaurant = new Restaurant(uuid, rname, winners, votes);
    	when(restaurantRepository.save(validRestaurant)).thenReturn(validRestaurant);
    	restaurantService.saveRestaurant(validRestaurant);
    	verify(restaurantRepository).save(validRestaurant);
    }
    
    @Test
    public void shouldSaveRestaurantWithNullId() throws Exception {
    	String uuid = null;
    	String	rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Restaurant validRestaurant = new Restaurant(uuid, rname, winners, votes);
    	Restaurant anotherRestaurant = new Restaurant(UUID.randomUUID().toString(), rname, winners, votes);
    	when(restaurantRepository.save(validRestaurant)).thenReturn(anotherRestaurant);
    	restaurantService.saveRestaurant(validRestaurant);
    	verify(restaurantRepository).save(validRestaurant);
    	assertNotEquals(anotherRestaurant, validRestaurant);
    }

    @Test
    public void shouldSaveRestaurantWithEmptyId() throws Exception {
    	String uuid = "";
    	String	rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Restaurant validRestaurant = new Restaurant(uuid, rname, winners, votes);
    	Restaurant anotherRestaurant = new Restaurant(UUID.randomUUID().toString(), rname, winners, votes);
    	when(restaurantRepository.save(validRestaurant)).thenReturn(anotherRestaurant);
    	restaurantService.saveRestaurant(validRestaurant);
    	verify(restaurantRepository).save(validRestaurant);
    	assertNotEquals(anotherRestaurant, validRestaurant);
    }
    
    @Test
    public void shouldUpdateRestaurant() throws Exception {
    	String uuid = mockId;
    	String	rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Restaurant validRestaurant = new Restaurant(uuid, rname, winners, votes);
    	restaurantService.updateRestaurant(validRestaurant);
    }
    
    @Test
    public void shouldDeleteRestaurantByid() throws Exception {
    	doNothing().when(restaurantRepository).delete(mockId);
    	restaurantService.deleteRestaurantById(mockId);
    	verify(restaurantRepository).delete(mockId);
    }
    
    @Test
    public void shouldDeleteAllRestaurants() throws Exception {
    	doNothing().when(restaurantRepository).deleteAll();
    	restaurantService.deleteAllRestaurants();
    	verify(restaurantRepository).deleteAll();
    }
    
    @Test
    public void shouldFindAllRestaurants() throws Exception {
    	List<Restaurant> validRestaurants = new ArrayList<>();
    	validRestaurants.add(mockRestaurant);
    	when(restaurantRepository.findAll()).thenReturn(validRestaurants);
    	List<Restaurant> restaurants = restaurantService.findAllRestaurants();
    	assertNotNull(restaurants);
    	assertEquals(restaurants, validRestaurants);
    	verify(restaurantRepository).findAll();
    }
    
    @Test
    public void shouldVerifyIsRestaurantExist() throws Exception {
    	when(restaurantService.findByName(mockRestaurant.getName())).thenReturn(mockRestaurant);
    	boolean valid = restaurantService.isRestaurantExist(mockRestaurant);
    	assertNotNull(valid);
    	assertTrue(valid);
    }
    
    @Test
    public void shouldFailToVerifyIsRestaurantExist() throws Exception {
    	when(restaurantService.findByName(mockRestaurant.getName())).thenReturn(null);
    	boolean valid = restaurantService.isRestaurantExist(mockRestaurant);
    	assertNotNull(valid);
    	assertFalse(valid);
    }
}
