package com.dbserver.voting.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
		if(winner.getId() == null  || winner.getId().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			winner.setId(uuid.toString());
		}
		if(winner.getLunchDate() == null) {
			winner.setLunchDate(new Date(Calendar.getInstance().getTimeInMillis()));
		}
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
