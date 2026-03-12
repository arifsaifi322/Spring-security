package com.incapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.incapp.entity.my_user;
import com.incapp.repo.repo;

@Service
public class service {
	
	@Autowired
	repo repo;
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	public my_user findByEmail(String email) {
		my_user u = repo.findById(email).orElse(null);
		return u;
	}

	public boolean registerUser(my_user user) {
			
		my_user u = repo.findById(user.getEmail()).orElse(null);
		if (u == null) {
			
			BCryptPasswordEncoder b = getPasswordEncoder();
			user.setPassword(b.encode(user.getPassword()));
			
			repo.save(user);
			return true;
		}else {
			return false;
		}
		
	}

}
