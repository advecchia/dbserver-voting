package com.dbserver.voting.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.VotePK;
import com.dbserver.voting.repositories.VoteRepository;

@Service("voteService")
@Transactional
public class DefaultVoteService implements VoteService {

	@Autowired
    private VoteRepository voteRepository;

	@Override
	public Vote findByIds(String userId, String restaurantId, Date votingDate) {
		return voteRepository.findOne(new VotePK(userId, restaurantId, votingDate));
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
	public void deleteVoteById(String userId, String restaurantId, Date votingDate) {
		voteRepository.delete(new VotePK(userId, restaurantId, votingDate));
		
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
		return voteRepository.findOne(new VotePK(vote.getUserId(), vote.getRestaurantId(), vote.getVotingDate())) != null;
	}

}
