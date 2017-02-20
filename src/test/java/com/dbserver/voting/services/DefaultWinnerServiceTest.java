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
import com.dbserver.voting.models.Vote;
import com.dbserver.voting.models.Winner;
import com.dbserver.voting.repositories.WinnerRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(DefaultWinnerService.class)

public class DefaultWinnerServiceTest {

private static final String mockId = UUID.randomUUID().toString();
	
	@Mock
	WinnerRepository winnerRepository;

	@InjectMocks
	WinnerService winnerService = new DefaultWinnerService();
    
    @Mock
    Winner mockWinner;
    
    @Before
    public void setup() throws Exception {
    	String rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());

    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	mockWinner = new Winner(mockId, lunchDate, restaurant);
    }
    
    @Test
    public void shouldFindbyId() throws Exception {
    	String restaurantId = mockId;
    	when(winnerRepository.findOne(restaurantId)).thenReturn(mockWinner);
    	Winner winner = winnerService.findById(restaurantId);
    	assertNotNull(winner);
    	assertEquals(winner, mockWinner);
    	verify(winnerRepository).findOne(restaurantId);
    }
    
    @Test
    public void shouldFindbyLunchDate() throws Exception {
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());
    	when(winnerRepository.findByLunchDate(lunchDate)).thenReturn(mockWinner);
    	Winner winner = winnerService.findByLunchDate(lunchDate);
    	assertNotNull(winner);
    	assertEquals(winner, mockWinner);
    	verify(winnerRepository).findByLunchDate(lunchDate);
    }
    
    @Test
    public void shouldSaveWinner() throws Exception {
    	String rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());

    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Winner validWinner = new Winner(mockId, lunchDate, restaurant);
    	when(winnerRepository.save(validWinner)).thenReturn(validWinner);
    	winnerService.saveWinner(validWinner);
    	verify(winnerRepository).save(validWinner);
    }
    
    @Test
    public void shouldSaveWinnerWithNullId() throws Exception {
    	String 	uuid = null,
    			rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());

    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Winner validWinner = new Winner(uuid, lunchDate, restaurant);
    	Winner anotherWinner= new Winner(UUID.randomUUID().toString(), lunchDate, restaurant);
    	when(winnerRepository.save(validWinner)).thenReturn(anotherWinner);
    	winnerService.saveWinner(validWinner);
    	verify(winnerRepository).save(validWinner);
    	assertNotEquals(anotherWinner, validWinner);
    }

    @Test
    public void shouldSaveWinnerWithEmptyId() throws Exception {
    	String uuid = "",
    			rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());

    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Winner validWinner = new Winner(uuid, lunchDate, restaurant);
    	Winner anotherWinner= new Winner(UUID.randomUUID().toString(), lunchDate, restaurant);
    	when(winnerRepository.save(validWinner)).thenReturn(anotherWinner);
    	winnerService.saveWinner(validWinner);
    	verify(winnerRepository).save(validWinner);
    	assertNotEquals(anotherWinner, validWinner);
    }
    
    @Test
    public void shouldSaveWinnerWithNullLunchDate() throws Exception {
    	String uuid = mockId,
    			rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date invalidDate = null;
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());

    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Winner validWinner = new Winner("", invalidDate, restaurant);
    	Winner anotherWinner= new Winner(uuid, lunchDate, restaurant);
    	when(winnerRepository.save(validWinner)).thenReturn(anotherWinner);
    	winnerService.saveWinner(validWinner);
    	verify(winnerRepository).save(validWinner);
    	assertNotEquals(anotherWinner, validWinner);
    }
    
    @Test
    public void shouldUpdateWinner() throws Exception {
    	String rname = "rname";
    	Set<Winner> winners = new HashSet<Winner>();
    	Set<Vote> votes = new HashSet<Vote>();
    	Date lunchDate = new Date(Calendar.getInstance().getTimeInMillis());

    	Restaurant restaurant = new Restaurant(mockId, rname, winners, votes);

    	Winner validWinner = new Winner(mockId, lunchDate, restaurant);
    	winnerService.updateWinner(validWinner);
    }
    
    @Test
    public void shouldDeleteWinnerByid() throws Exception {
    	doNothing().when(winnerRepository).delete(mockId);
    	winnerService.deleteWinnerById(mockId);
    	verify(winnerRepository).delete(mockId);
    }
    
    @Test
    public void shouldDeleteAllWinners() throws Exception {
    	doNothing().when(winnerRepository).deleteAll();
    	winnerService.deleteAllWinners();
    	verify(winnerRepository).deleteAll();
    }
    
    @Test
    public void shouldFindAllWinners() throws Exception {
    	List<Winner> validWinners = new ArrayList<>();
    	validWinners.add(mockWinner);
    	when(winnerRepository.findAll()).thenReturn(validWinners);
    	List<Winner> winners = winnerService.findAllWinners();
    	assertNotNull(winners);
    	assertEquals(winners, validWinners);
    	verify(winnerRepository).findAll();
    }
    
    @Test
    public void shouldVerifyIsWinnerExist() throws Exception {
    	when(winnerRepository.findByLunchDate(mockWinner.getLunchDate())).thenReturn(mockWinner);
    	boolean valid = winnerService.isWinnerExist(mockWinner);
    	assertNotNull(valid);
    	assertTrue(valid);
    }
    
    @Test
    public void shouldFailToVerifyIsWinnerExist() throws Exception {
    	when(winnerRepository.findByLunchDate(mockWinner.getLunchDate())).thenReturn(null);
    	boolean valid = winnerService.isWinnerExist(mockWinner);
    	assertNotNull(valid);
    	assertFalse(valid);
    }
}
