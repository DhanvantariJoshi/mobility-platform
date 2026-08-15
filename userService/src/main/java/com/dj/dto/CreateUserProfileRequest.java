package com.dj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserProfileRequest {

	private Long authUserId;

	private String mobileNumber;

	private String firstName;

	private String lastName;

	private String email;

}
