package com.dbserver.voting.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Base64;
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
import com.dbserver.voting.models.User;
import com.dbserver.voting.models.Vote;
import com.dbserver.voting.repositories.UserRepository;
import com.dbserver.voting.util.Encryptor;


@RunWith(SpringRunner.class)
@WebMvcTest(DefaultUserService.class)
public class DefaultUserServiceTest {
	
	private static final String mockId = UUID.randomUUID().toString();

	@Mock
	UserRepository userRepository;

	@InjectMocks
    UserService userService = new DefaultUserService();
    
    @Mock
    User mockUser;
    
    @Before
    public void setup() throws Exception {
    	String	uname = "uname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	mockUser = new User(mockId, uname, username, password, votes);
    }
    
    @Test
    public void shouldFindbyId() throws Exception {
    	when(userRepository.findOne(mockId)).thenReturn(mockUser);
    	User user = userService.findById(mockId);
    	assertNotNull(user);
    	assertEquals(user, mockUser);
    	verify(userRepository).findOne(mockId);
    }
    
    @Test
    public void shouldFindbyName() throws Exception {
    	String name = "uname";
    	when(userRepository.findByName(name)).thenReturn(mockUser);
    	User user = userService.findByName(name);
    	assertNotNull(user);
    	assertEquals(user, mockUser);
    	verify(userRepository).findByName(name);
    }
    
    @Test
    public void shouldFindbyUsername() throws Exception {
    	String username = "username";
    	when(userRepository.findByUsername(username)).thenReturn(mockUser);
    	User user = userService.findByUsername(username);
    	assertNotNull(user);
    	assertEquals(user, mockUser);
    	verify(userRepository).findByUsername(username);
    }
    
    @Test
    public void shouldSaveUser() throws Exception {
    	String uuid = UUID.randomUUID().toString();
    	String	uname = "uname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	when(userRepository.save(validUser)).thenReturn(validUser);
    	userService.saveUser(validUser);
    	verify(userRepository).save(validUser);
    }
    
    @Test
    public void shouldSaveUserWithNullId() throws Exception {
    	String uuid = null;
    	String	uname = "uname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	User anotherUser = new User(UUID.randomUUID().toString(), uname, username, password, votes);
    	when(userRepository.save(validUser)).thenReturn(anotherUser);
    	userService.saveUser(validUser);
    	verify(userRepository).save(validUser);
    	assertNotEquals(anotherUser, validUser);
    }

    @Test
    public void shouldSaveUserWithEmptyId() throws Exception {
    	String uuid = "";
    	String	uname = "uname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	User anotherUser = new User(UUID.randomUUID().toString(), uname, username, password, votes);
    	when(userRepository.save(validUser)).thenReturn(anotherUser);
    	userService.saveUser(validUser);
    	verify(userRepository).save(validUser);
    	assertNotEquals(anotherUser, validUser);
    }

    @Test
    public void shouldFailToSaveUser() throws Exception {
    	String uuid = UUID.randomUUID().toString();
    	String	uname = "uname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	doThrow(Exception.class).when(userRepository).save(validUser);
    	userService.saveUser(validUser);
    	verify(userRepository).save(validUser);
    }
    
    @Test
    public void shouldUpdateUser() throws Exception {
    	String uuid = mockId;
    	String	uname = "uname",
    			username = "username", 
				password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	userService.updateUser(validUser);
    }
    
    @Test
    public void shouldDeleteUserByid() throws Exception {
    	doNothing().when(userRepository).delete(mockId);
    	userService.deleteUserById(mockId);
    	verify(userRepository).delete(mockId);
    }
    
    @Test
    public void shouldDeleteAllUsers() throws Exception {
    	doNothing().when(userRepository).deleteAll();
    	userService.deleteAllUsers();
    	verify(userRepository).deleteAll();
    }
    
    @Test
    public void shouldFindAllUsers() throws Exception {
    	List<User> validUsers = new ArrayList<>();
    	validUsers.add(mockUser);
    	when(userRepository.findAll()).thenReturn(validUsers);
    	List<User> users = userService.findAllUsers();
    	assertNotNull(users);
    	assertEquals(users, validUsers);
    	verify(userRepository).findAll();
    }
    
    @Test
    public void shouldVerifyIsUserExist() throws Exception {
    	when(userService.findByUsername(mockUser.getUsername())).thenReturn(mockUser);
    	boolean valid = userService.isUserExist(mockUser);
    	assertNotNull(valid);
    	assertTrue(valid);
    }
    
    @Test
    public void shouldFailToVerifyIsUserExist() throws Exception {
    	when(userService.findByUsername(mockUser.getUsername())).thenReturn(null);
    	boolean valid = userService.isUserExist(mockUser);
    	assertNotNull(valid);
    	assertFalse(valid);
    }

    @Test
    public void shouldAuthorizeUser() throws Exception {
    	String uuid = mockId;
    	String	uname = "uname",
    			username = "username", 
    			password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	Encryptor encryptor = new Encryptor();
    	User existingUser = new User(uuid, uname, username, encryptor.encrypt("password"), votes);
    	when(userRepository.findByUsername(username)).thenReturn(existingUser);
    	User user = userService.authorize(validUser);
    	assertNotNull(user);
    	assertEquals(user, existingUser);
    	verify(userRepository).findByUsername(username);
    }
    
    @Test
    public void shouldFailToAuthorizeUserInvalidUsername() throws Exception {
    	String uuid = mockId;
    	String	uname = "uname",
    			username = "username", 
    			password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	User existingUser = null;
    	when(userRepository.findByUsername(username)).thenReturn(existingUser);
    	User user = userService.authorize(validUser);
    	assertNull(user);
    	assertEquals(user, existingUser);
    	verify(userRepository).findByUsername(username);
    }
    
    @Test
    public void shouldFailToAuthorizeUserInvalidPassword() throws Exception {
    	String uuid = mockId;
    	String	uname = "uname",
    			username = "username", 
    			password = new String(Base64.getEncoder().encode("password".getBytes()));
    	Set<Vote> votes = new HashSet<Vote>();
    	User validUser = new User(uuid, uname, username, password, votes);
    	Encryptor encryptor = new Encryptor();
    	User existingUser = new User(uuid, uname, username, encryptor.encrypt("password2"), votes);
    	when(userRepository.findByUsername(username)).thenReturn(existingUser);
    	User user = userService.authorize(validUser);
    	assertNull(user);
    	assertNotEquals(user, existingUser);
    	verify(userRepository).findByUsername(username);
    }
}
