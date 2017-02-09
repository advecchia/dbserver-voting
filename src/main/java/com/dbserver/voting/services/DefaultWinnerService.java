package com.dbserver.voting.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbserver.voting.models.Winner;
import com.dbserver.voting.repositories.WinnerRepository;

@Service("winnerService")
@Transactional
public class DefaultWinnerService implements WinnerService {

	@Autowired
    private WinnerRepository winnerRepository;

	@Override
	public Winner findById(String restaurantId) {
		return winnerRepository.findOne(restaurantId);
	}

	@Override
	public Winner findByLunchDate(Date lunchDate) {
		return winnerRepository.findByLunchDate(lunchDate);
	}

	@Override
	public void saveWinner(Winner winner) {
		winnerRepository.save(winner);
		
	}

	@Override
	public void updateWinner(Winner winner) {
		saveWinner(winner);
		
	}

	@Override
	public void deleteWinnerById(String restaurantId) {
		winnerRepository.delete(restaurantId);
		
	}

	@Override
	public void deleteAllWinners() {
		winnerRepository.deleteAll();
		
	}

	@Override
	public List<Winner> findAllWinners() {
		return winnerRepository.findAll();
	}

	@Override
	public boolean isWinnerExist(Winner winner) {
		return winnerRepository.findByLunchDate(winner.getLunchDate()) != null;
	}

}
