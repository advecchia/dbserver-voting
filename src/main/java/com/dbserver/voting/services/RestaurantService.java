package com.dbserver.voting.services;

import java.util.List;

import com.dbserver.voting.models.Restaurant;

public interface RestaurantService {
	Restaurant findById(String id);
	 
	Restaurant findByName(String name);
 
    void saveRestaurant(Restaurant restaurant);
 
    void updateRestaurant(Restaurant restaurant);
 
    void deleteRestaurantById(String id);
 
    void deleteAllRestaurants();
 
    List<Restaurant> findAllRestaurants();
 
    boolean isRestaurantExist(Restaurant restaurant);
}
