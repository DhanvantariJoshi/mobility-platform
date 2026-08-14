package com.dj.dto;

import com.dj.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

	private String mobileNumber;

	private String firstName;

	private String lastName;

	private String email;

	private String password;
	
	private Role role;


}
