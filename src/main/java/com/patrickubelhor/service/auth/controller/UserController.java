package com.patrickubelhor.service.auth.controller;

import com.patrickubelhor.service.auth.exception.InvalidFieldException;
import com.patrickubelhor.service.auth.model.NewUser;
import com.patrickubelhor.service.auth.model.User;
import com.patrickubelhor.service.auth.service.AuthService;
import com.patrickubelhor.service.auth.service.FieldValidator;
import com.patrickubelhor.service.auth.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	private final FieldValidator fieldValidator;
	private final AuthService authService;
	private final UserService userService;
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	public UserController(
			FieldValidator fieldValidator,
			AuthService authService,
			UserService userService
	) {
		this.fieldValidator = fieldValidator;
		this.authService = authService;
		this.userService = userService;
	}
	
	
	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(
			@Valid @RequestBody NewUser newUser) {
		
		logger.info("Received createUser request");
		fieldValidator.validateUser(newUser);
		User user = userService.createUser(newUser);
		logger.info("Created user " + user.getEmail());
		
		String token = authService.generateSessionToken(user.getId());
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.header("token", token)
				.body(user);
	}
	
	
	@GetMapping(value = "/user")
	public ResponseEntity<User> getUser(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "email", required = false) String email,
			@RequestHeader("token") String token) {
		
		logger.info("Received getUser request");
		authService.validateTokenString(token);
		
		User result = userService.queryUsers(id, username, email);
		
		// If no query params specified, get self
		if (result == null) {
			result = userService.getUser(authService.getUserIdFromToken(token));
		}
		
		return ResponseEntity.ok(result);
	}
	
	
	@PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(
			@RequestHeader("token") String token,
			@Valid @RequestBody NewUser user) {
		
		logger.info("Received updateUser request");
		authService.validateTokenString(token);
		
		if (!fieldValidator.validateOneNonNull(user)) {
			throw new InvalidFieldException("One or more fields must be non-empty");
		}
		
		Long id = authService.getUserIdFromToken(token);
		User result = userService.updateUser(id, user);
		
		return ResponseEntity.ok(result);
	}
	
	
	@DeleteMapping(value = "/user")
	public ResponseEntity<Void> deleteUser(
			@RequestHeader("token") String token) {
		
		logger.info("Received deleteUser request");
		authService.validateTokenString(token);
		
		Long id = authService.getUserIdFromToken(token);
		authService.revokeToken(token);
		userService.deleteUser(id);
		
		return ResponseEntity.noContent().build();
	}
	
	/*
	@GetMapping(value = "/search")
	public ResponseEntity<List<UserSearchResponse>> searchUsers(
			@RequestHeader("token") String token,
			@RequestHeader("query") String query) {

		logger.info("Received searchUsers request");
		authService.validateTokenString(token);

		Long requesterId = authService.getUserIdFromToken(token);
		List<User> users = userService.searchUsers(query);

		List<UserSearchResponse> result = new LinkedList<>();
		for (User user : users) {
			if (user.getId().equals(requesterId)) {
				continue;
			}

			UserResponse userResponse = UserResponse.convert(user);
			Relationship relationship = friendshipService.getRelationship(requesterId, user.getId());
			result.add(new UserSearchResponse(userResponse, relationship));
		}

		return ResponseEntity.ok(result);
	}
	*/
}
