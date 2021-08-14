package com.patrickubelhor.service.auth.controller;

import com.patrickubelhor.service.auth.model.User;
import com.patrickubelhor.service.auth.service.AuthService;
import com.patrickubelhor.service.auth.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
	
	private final AuthService authService;
	private final UserService userService;
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
	@Autowired
	public AuthController(AuthService authService, UserService userService) {
		this.authService = authService;
		this.userService = userService;
	}
	
	
	@GetMapping("/login")
	public ResponseEntity<User> login(
			@RequestHeader("email") String email,
			@RequestHeader("password") String password
	) {
		logger.info("Received login request");
		User target = userService.authenticate(email, password);
		String token = authService.generateSessionToken(target.getId());
		
		// TODO: Use typical token/bearer terminology
		return ResponseEntity.status(HttpStatus.OK)
				.header("token", token)
				.body(target);
	}
	
	
	@GetMapping("/verify")
	public ResponseEntity<Void> login(
			@RequestHeader("token") String token
	) {
		logger.info("Received verification request");
		authService.getUserIdFromToken(token); // Verify it exists
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	@GetMapping("/logout")
	public ResponseEntity<Void> logout(
			@RequestHeader("token") String token
	) {
		logger.info("Received logout request");
		authService.getUserIdFromToken(token); // Verify it exists
		authService.revokeToken(token);
		
		return ResponseEntity.ok().build();
	}
	
}
