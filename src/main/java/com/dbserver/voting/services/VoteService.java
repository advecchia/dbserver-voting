package com.dbserver.voting.services;

import java.sql.Date;
import java.util.List;

import com.dbserver.voting.models.Vote;

public interface VoteService {
	Vote findByIds(String userId, String restaurantId, Date votingDate);

	List<Vote> findByVotingDate(Date votingDate);

	List<Vote> findByUserId(String userId);
	
	List<Vote> findByRestaurantId(String restaurantId);
 
    void saveVote(Vote vote);
 
    void updateVote(Vote vote);
 
    void deleteVoteById(String userId, String restaurantId, Date votingDate);
 
    void deleteAllVotes();
 
    List<Vote> findAllVotes();
 
    boolean isVoteExist(Vote vote);
}
