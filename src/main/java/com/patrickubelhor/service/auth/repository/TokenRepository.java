package com.patrickubelhor.service.auth.repository;

import com.patrickubelhor.service.auth.model.TokenListing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenListing, Long> {
	
	Optional<TokenListing> getTokenByToken(String token);
	void deleteTokenByToken(String token);
	
}
