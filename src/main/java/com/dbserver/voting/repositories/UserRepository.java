package com.dbserver.voting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbserver.voting.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String name);
	
	@Query(value = "select * from users u where u.username = ?1", nativeQuery = true)
	User findByUsername(String username);
	
	@Query(value = "select * from users u where u.username = ?1 and cast(aes_decrypt(from_base64(u.password), sha2(\"-my-salt-string-\",256)) as char) = ?2", nativeQuery = true)
	User validateUser(String username, String password);
}
