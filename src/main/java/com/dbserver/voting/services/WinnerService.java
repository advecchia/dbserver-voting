package com.dbserver.voting.services;

import java.sql.Date;
import java.util.List;

import com.dbserver.voting.models.Winner;

public interface WinnerService {
	Winner findById(String restaurantId);
	 
	Winner findByLunchDate(Date lunchDate);
 
    void saveWinner(Winner winner);
 
    void updateWinner(Winner winner);
 
    void deleteWinnerById(String restaurantId);
 
    void deleteAllWinners();
 
    List<Winner> findAllWinners();
 
    boolean isWinnerExist(Winner winner);
}
