package com.dbserver.voting.services;

import java.sql.Date;
import java.util.List;

import com.dbserver.voting.models.Vote;

public interface VoteService {
	Vote findById(String id);

	List<Vote> findByVotingDate(Date votingDate);

	List<Vote> findByUserId(String userId);
	
	List<Vote> findByRestaurantId(String restaurantId);
 
    void saveVote(Vote vote);
 
    void updateVote(Vote vote);
 
    void deleteVoteById(String id);
 
    void deleteAllVotes();
 
    List<Vote> findAllVotes();
 
    boolean isVoteExist(Vote vote);
    
    public void executeVoting(Date votingDate);
}
