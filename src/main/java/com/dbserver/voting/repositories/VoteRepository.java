package com.dbserver.voting.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.VotePK;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VotePK> {
	
	@Query(value = "select * from votes v where v.userId = ?1 and v.restaurantId = ?2 and v.votingDate = ?3", nativeQuery = true)
	Vote findByIds(String userId, String restaurantId, Date votingDate);

	@Query(value = "select * from votes v where v.votingDate = ?1", nativeQuery = true)
	List<Vote> findByVotingDate(Date votingDate);
	
	@Query(value = "select * from votes v where v.userId = ?1", nativeQuery = true)
	List<Vote> findByUserId(String userId);

	@Query(value = "select * from votes v where v.restaurantId = ?1", nativeQuery = true)
	List<Vote> findByRestaurantId(String restaurantId);

}
