package com.patrickubelhor.service.auth.repository;

import com.patrickubelhor.service.auth.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findUserByUsername(@Param("username") String username);
	
	@Query(value = "SELECT user from User user WHERE LOWER(user.email) = LOWER(:email)")
	Optional<User> findUserByEmail(@Param("email") String email);
	
	@Query(value = "SELECT users FROM User users WHERE LOWER(users.username) LIKE LOWER('%' || :query || '%')")
	List<User> searchUsers(@Param("query") String query);

}
