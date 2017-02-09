package com.dbserver.voting.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbserver.voting.models.User;
import com.dbserver.voting.repositories.UserRepository;

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
 
    public void saveUser(User user) {
    	if(user.getId() == null  || user.getId().isEmpty()) {
    		UUID uuid = UUID.randomUUID();
    		user.setId(uuid.toString());
    	}
        userRepository.save(user);
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
        return findByName(user.getName()) != null;
    }
}
