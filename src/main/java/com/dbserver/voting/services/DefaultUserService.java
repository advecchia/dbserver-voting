package com.dbserver.voting.services;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbserver.voting.models.User;
import com.dbserver.voting.repositories.UserRepository;
import com.dbserver.voting.util.Encryptor;

@Service("userService")
@Transactional
public class DefaultUserService implements UserService {

	@Autowired
    private UserRepository userRepository;
 
    public User findById(String id) {
        return userRepository.findOne(id);
    }
 
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
    	try {
	    	if(user.getId() == null  || user.getId().isEmpty()) {
	    		UUID uuid = UUID.randomUUID();
	    		user.setId(uuid.toString());
	    	}
	    	Encryptor encryptor = new Encryptor();
	    	user.setPassword(encryptor.encrypt(new String(Base64.getDecoder().decode(user.getPassword()))));
	        userRepository.save(user);
    	} catch (Exception e) {
    		
    	}
    }
 
    public void updateUser(User user){
        saveUser(user);
    }
 
    public void deleteUserById(String id){
        userRepository.delete(id);
    }
 
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
 
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
 
    public boolean isUserExist(User user) {
        return findByUsername(user.getUsername()) != null;
    }

    public User authorize(User user) {
    	User existingUser = userRepository.findByUsername(user.getUsername());
    	if (existingUser != null) {
        	Encryptor encryptor = new Encryptor();
    		Boolean equalPasswords = encryptor.encrypt(new String(Base64.getDecoder().decode(user.getPassword()))).equals(existingUser.getPassword());
    		if (equalPasswords) { 	
    			return existingUser;
    		}
    	}

		return null;		
    }
}
