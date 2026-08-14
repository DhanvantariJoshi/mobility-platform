package com.dj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OtpResponse {

	private String message;

	private Boolean verified;

}
