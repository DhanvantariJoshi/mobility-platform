package com.dj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dj.dto.CreateUserProfileRequest;
import com.dj.dto.UserProfileResponse;
import com.dj.entity.UserProfile;
import com.dj.repositiory.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

	private final UserProfileRepository userProfileRepository;

	@GetMapping("/{authUserId}")
	public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long authUserId) {

		UserProfile user = userProfileRepository.findByAuthUserId(authUserId)
				.orElseThrow(() -> new RuntimeException("User profile not found"));

		UserProfileResponse response = UserProfileResponse.builder().id(user.getId()).authUserId(user.getAuthUserId())
				.mobileNumber(user.getMobileNumber()).firstName(user.getFirstName()).lastName(user.getLastName())
				.email(user.getEmail()).profileImage(user.getProfileImage()).build();

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<UserProfileResponse> createProfile(@RequestBody CreateUserProfileRequest request) {

		UserProfile userProfile = UserProfile.builder().authUserId(request.getAuthUserId())
				.mobileNumber(request.getMobileNumber()).firstName(request.getFirstName())
				.lastName(request.getLastName()).email(request.getEmail()).build();

		UserProfile saved = userProfileRepository.save(userProfile);

		UserProfileResponse response = UserProfileResponse.builder().id(saved.getId()).authUserId(saved.getAuthUserId())
				.mobileNumber(saved.getMobileNumber()).firstName(saved.getFirstName()).lastName(saved.getLastName())
				.email(saved.getEmail()).profileImage(saved.getProfileImage()).build();

		return ResponseEntity.ok(response);
	}
}