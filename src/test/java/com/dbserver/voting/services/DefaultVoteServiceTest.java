package com.dbserver.voting.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dbserver.voting.models.Restaurant;
import com.dbserver.voting.models.User;
import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.Winner;
import com.dbserver.voting.repositories.VoteRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(DefaultVoteService.class)
public class DefaultVoteServiceTest {
	
	private static final String mockId = UUID.randomUUID().toString();
	
	@Mock
	VoteRepository voteRepository;

	@InjectMocks
	VoteService voteService = new DefaultVoteService();
    
    @Mock
    Vote mockVote;
    
    @Before
    public void setup() throws Exception {
    	String	uname = "uname",
    			rname = "rname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());

    	User user = new User(mockId, uname, username, password, votes);
    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	mockVote = new Vote(mockId, votingDate, user, restaurant);
    }
    
    @Test
    public void shouldFindbyId() throws Exception {
    	when(voteRepository.findOne(mockId)).thenReturn(mockVote);
    	Vote vote = voteService.findById(mockId);
    	assertNotNull(vote);
    	assertEquals(vote, mockVote);
    	verify(voteRepository).findOne(mockId);
    }
    
    @Test
    public void shouldFindbyVotingDate() throws Exception {
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());
    	List<Vote> validVotes = new ArrayList<>();
    	validVotes.add(mockVote);
    	when(voteRepository.findByVotingDate(votingDate)).thenReturn(validVotes);
    	List<Vote> votes = voteService.findByVotingDate(votingDate);
    	assertNotNull(votes);
    	assertEquals(votes, validVotes);
    	verify(voteRepository).findByVotingDate(votingDate);
    }
    
    @Test
    public void shouldFindbyUserId() throws Exception {
    	List<Vote> validVotes = new ArrayList<>();
    	validVotes.add(mockVote);
    	when(voteRepository.findByUserId(mockId)).thenReturn(validVotes);
    	List<Vote> votes = voteService.findByUserId(mockId);
    	assertNotNull(votes);
    	assertEquals(votes, validVotes);
    	verify(voteRepository).findByUserId(mockId);
    }
    
    @Test
    public void shouldFindbyRestaurantId() throws Exception {
    	List<Vote> validVotes = new ArrayList<>();
    	validVotes.add(mockVote);
    	when(voteRepository.findByRestaurantId(mockId)).thenReturn(validVotes);
    	List<Vote> votes = voteService.findByRestaurantId(mockId);
    	assertNotNull(votes);
    	assertEquals(votes, validVotes);
    	verify(voteRepository).findByRestaurantId(mockId);
    }
    
    @Test
    public void shouldSaveVote() throws Exception {
    	String 	uname = "uname",
    			rname = "rname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());

    	User user = new User(mockId, uname, username, password, votes);
    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Vote validVote = new Vote(mockId, votingDate, user, restaurant);
    	when(voteRepository.save(validVote)).thenReturn(validVote);
    	voteService.saveVote(validVote);
    	verify(voteRepository).save(validVote);
    }
    
    @Test
    public void shouldSaveVoteWithNullId() throws Exception {
    	String uuid = null,
    			uname = "uname",
    			rname = "rname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());

    	User user = new User(mockId, uname, username, password, votes);
    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Vote validVote = new Vote(uuid, votingDate, user, restaurant);
    	Vote anotherVote = new Vote(UUID.randomUUID().toString(), votingDate, user, restaurant);
   	
    	when(voteRepository.save(validVote)).thenReturn(anotherVote);
    	voteService.saveVote(validVote);
    	verify(voteRepository).save(validVote);
    	assertNotEquals(anotherVote, validVote);
    }

    @Test
    public void shouldSaveVoteWithEmptyId() throws Exception {
    	String uuid = "",
    			uname = "uname",
    			rname = "rname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());

    	User user = new User(mockId, uname, username, password, votes);
    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Vote validVote = new Vote(uuid, votingDate, user, restaurant);
    	Vote anotherVote = new Vote(UUID.randomUUID().toString(), votingDate, user, restaurant);
   	
    	when(voteRepository.save(validVote)).thenReturn(anotherVote);
    	voteService.saveVote(validVote);
    	verify(voteRepository).save(validVote);
    	assertNotEquals(anotherVote, validVote);
    }
    
    @Test
    public void shouldSaveVoteWithNullVotingDate() throws Exception {
    	String uuid = mockId,
    			uname = "uname",
    			rname = "rname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date invalidDate = null;
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());

    	User user = new User(mockId, uname, username, password, votes);
    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Vote validVote = new Vote(uuid, invalidDate, user, restaurant);
    	Vote anotherVote = new Vote(UUID.randomUUID().toString(), votingDate, user, restaurant);
   	
    	when(voteRepository.save(validVote)).thenReturn(anotherVote);
    	voteService.saveVote(validVote);
    	verify(voteRepository).save(validVote);
    	assertNotEquals(anotherVote, validVote);
    }
    
    @Test
    public void shouldUpdateVote() throws Exception {
    	String 	uname = "uname",
    			rname = "rname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date votingDate = new Date(Calendar.getInstance().getTimeInMillis());

    	User user = new User(mockId, uname, username, password, votes);
    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Vote validVote = new Vote(mockId, votingDate, user, restaurant);
    	voteService.updateVote(validVote);
    }
    
    @Test
    public void shouldDeleteVoteByid() throws Exception {
    	doNothing().when(voteRepository).delete(mockId);
    	voteService.deleteVoteById(mockId);
    	verify(voteRepository).delete(mockId);
    }
    
    @Test
    public void shouldDeleteAllVotes() throws Exception {
    	doNothing().when(voteRepository).deleteAll();
    	voteService.deleteAllVotes();
    	verify(voteRepository).deleteAll();
    }
    
    @Test
    public void shouldFindAllVotes() throws Exception {
    	List<Vote> validVotes = new ArrayList<>();
    	validVotes.add(mockVote);
    	when(voteRepository.findAll()).thenReturn(validVotes);
    	List<Vote> votes = voteService.findAllVotes();
    	assertNotNull(votes);
    	assertEquals(votes, validVotes);
    	verify(voteRepository).findAll();
    }
    
    @Test
    public void shouldVerifyIsVoteExist() throws Exception {
    	when(voteRepository.findByData(mockVote.getId(), mockVote.getVotingDate())).thenReturn(mockVote);
    	boolean valid = voteService.isVoteExist(mockVote);
    	assertNotNull(valid);
    	assertTrue(valid);
    }
    
    @Test
    public void shouldFailToVerifyIsVoteExist() throws Exception {
    	when(voteRepository.findByData(mockVote.getId(), mockVote.getVotingDate())).thenReturn(null);
    	boolean valid = voteService.isVoteExist(mockVote);
    	assertNotNull(valid);
    	assertFalse(valid);
    }
    
    @Test
    public void shouldExecuteVoting() throws Exception {
    	String newVoteId = UUID.randomUUID().toString();
    	doNothing().when(voteRepository).executeVoting(newVoteId, mockVote.getVotingDate());
    	voteService.executeVoting(mockVote.getVotingDate());
    	assertNotNull(mockVote.getVotingDate());
    	assertNotNull(newVoteId);
    }
}
