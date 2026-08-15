package com.dj.service;

import com.dj.dto.CreateUserProfileRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

	private final RestClient restClient;

	public void createUserProfile(CreateUserProfileRequest request) {

		restClient.post().uri("http://localhost:8082/api/users").body(request).retrieve().toBodilessEntity();
	}
}
