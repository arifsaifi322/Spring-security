package com.incapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class my_user {
	@Id
	private String email;
	private String first_name;
	private String last_name;
	private String password;
	private String role = "ROLE_USER";
	
}
