package com.dbserver.voting.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbserver.voting.models.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String> {

	@Query(value = "select * from votes v where v.votingDate = ?1", nativeQuery = true)
	List<Vote> findByVotingDate(Date votingDate);
	
	@Query(value = "select * from votes v where v.userId = ?1", nativeQuery = true)
	List<Vote> findByUserId(String userId);

	@Query(value = "select * from votes v where v.restaurantId = ?1", nativeQuery = true)
	List<Vote> findByRestaurantId(String restaurantId);

	@Query(value = "select * from votes v where v.userId = ?1 and v.votingDate = ?2", nativeQuery = true)
	Vote findByData(String userId, Date votingDate);

	@Modifying
	@Query(value = "insert into winners(id, restaurantId, lunchDate) values (?1, (select r.id from restaurants as r "
			+ "inner join (select v.restaurantId, count(v.restaurantId) as voteNumber from votes as v "
			+ "where v.votingDate=?2 "
			+ "group by v.restaurantId order by voteNumber desc limit 1) as vi "
			+ "on (r.id = vi.restaurantId)), ?2)", nativeQuery = true)
	void executeVoting(String id, Date votingDate);
}
