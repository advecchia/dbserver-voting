package com.dbserver.voting.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbserver.voting.models.Restaurant;
import com.dbserver.voting.repositories.RestaurantRepository;

@Service("restaurantService")
@Transactional
public class DefaultRestaurantService implements RestaurantService {

	@Autowired
    private RestaurantRepository restaurantRepository;

	@Override
	public Restaurant findById(String id) {
		return restaurantRepository.findOne(id);
	}

	@Override
	public Restaurant findByName(String name) {
		return restaurantRepository.findByName(name);
	}

	@Override
	public void saveRestaurant(Restaurant restaurant) {
		if(restaurant.getId() == null  || restaurant.getId().isEmpty()) {
    		UUID uuid = UUID.randomUUID();
    		restaurant.setId(uuid.toString());
    	}
		restaurantRepository.save(restaurant);
		
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) {
		saveRestaurant(restaurant);
		
	}

	@Override
	public void deleteRestaurantById(String id) {
		restaurantRepository.delete(id);
		
	}

	@Override
	public void deleteAllRestaurants() {
		restaurantRepository.deleteAll();
		
	}

	@Override
	public List<Restaurant> findAllRestaurants() {
		return restaurantRepository.findAll();
	}

	@Override
	public boolean isRestaurantExist(Restaurant restaurant) {
		return restaurantRepository.findByName(restaurant.getName()) != null;
	}
	
}
