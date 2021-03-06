package com.patrickubelhor.service.auth.service;

import com.patrickubelhor.service.auth.exception.InvalidFieldException;
import com.patrickubelhor.service.auth.model.NewUser;
import org.springframework.stereotype.Service;

@Service
public class FieldValidator {

	public boolean validateUser(NewUser user) throws InvalidFieldException {
		
		validateNonNullString(user.getUsername(), "username");
		validateNonNullString(user.getPassword(), "password");
		validateNonNullString(user.getEmail(), "email");
		
		return true;
	}
	
	
	private void validateNonNullString(String field, String fieldName) throws InvalidFieldException {
		if (field == null || field.isBlank())
			throw new InvalidFieldException("Field <" + fieldName + "> cannot be null/empty.");
	}
	
	
	/**
	 * Ensures that at least one of the listed parameters is non-null.
	 *
	 * @param newUser The object to validate
	 */
	public boolean validateOneNonNull(NewUser newUser) {
		
		if (newUser.getUsername() != null && !newUser.getUsername().isBlank()) {
			return true;
		}
		
		if (newUser.getPassword() != null && !newUser.getPassword().isBlank()) {
			return true;
		}
		
		if (newUser.getEmail() != null && !newUser.getEmail().isBlank()) {
			return true;
		}
		
		return false;
	}
	
}
