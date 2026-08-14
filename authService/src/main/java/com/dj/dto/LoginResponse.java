package com.dj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

	private Long userId;

	private String mobileNumber;

	private String role;

	private String token;

	private String message;

}
