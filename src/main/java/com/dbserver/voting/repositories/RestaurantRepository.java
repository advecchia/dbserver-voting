package com.dbserver.voting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbserver.voting.models.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
	
	Restaurant findByName(String name);

}
