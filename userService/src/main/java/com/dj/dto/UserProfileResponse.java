package com.dj.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

	private Long id;

	private Long authUserId;

	private String mobileNumber;

	private String firstName;

	private String lastName;

	private String email;

	private String profileImage;
}