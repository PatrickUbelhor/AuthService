package com.patrickubelhor.service.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Disable CSRF (cross site request forgery)
//		http.csrf().disable();
		
		http.authorizeRequests()
				.anyRequest()
				.permitAll()
				.and()
				.cors();
//				.and()
//				.csrf();
//				.and()
//				.requiresChannel()
//				.anyRequest()
//				.requiresSecure();
	}
	
}
