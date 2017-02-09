package com.dbserver.voting.services;

import java.util.List;

import com.dbserver.voting.models.User;

public interface UserService {
	User findById(String id);
	 
	User findByName(String name);
 
    void saveUser(User user);
 
    void updateUser(User user);
 
    void deleteUserById(String id);
 
    void deleteAllUsers();
 
    List<User> findAllUsers();
 
    boolean isUserExist(User user);
}
