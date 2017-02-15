package com.dbserver.voting.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbserver.voting.models.Vote;
import com.dbserver.voting.repositories.VoteRepository;

@Service("voteService")
@Transactional
public class DefaultVoteService implements VoteService {

	@Autowired
    private VoteRepository voteRepository;

	@Override
	public Vote findById(String id) {
		return voteRepository.findOne(id);
	}

	@Override
	public List<Vote> findByVotingDate(Date votingDate) {
		return voteRepository.findByVotingDate(votingDate);
	}

	@Override
	public List<Vote> findByUserId(String userId) {
		return voteRepository.findByUserId(userId);
	}

	@Override
	public List<Vote> findByRestaurantId(String restaurantId) {
		return voteRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public void saveVote(Vote vote) {
		if(vote.getId() == null  || vote.getId().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			vote.setId(uuid.toString());
		}
		if(vote.getVotingDate() == null) {
			vote.setVotingDate(new Date(Calendar.getInstance().getTimeInMillis()));
		}
		voteRepository.save(vote);
		
	}

	@Override
	public void updateVote(Vote vote) {
		saveVote(vote);
		
	}

	@Override
	public void deleteVoteById(String id) {
		voteRepository.delete(id);
		
	}

	@Override
	public void deleteAllVotes() {
		voteRepository.deleteAll();
		
	}

	@Override
	public List<Vote> findAllVotes() {
		return voteRepository.findAll();
	}

	@Override
	public boolean isVoteExist(Vote vote) {
		return voteRepository.findByData(vote.getUser().getId(), vote.getVotingDate()) != null;
	}
	
	@Override
	public void executeVoting(Date votingDate) {
		UUID uuid = UUID.randomUUID();
		voteRepository.executeVoting(uuid.toString(), votingDate);
	}

}
