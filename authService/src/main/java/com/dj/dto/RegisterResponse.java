package com.dj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterResponse {

	private Long id;

	private String mobileNumber;

	private String firstName;

	private String lastName;

	private String email;

	private String message;
}
