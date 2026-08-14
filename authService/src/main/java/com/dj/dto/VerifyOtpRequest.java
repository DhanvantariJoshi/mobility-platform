package com.dj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VerifyOtpRequest {

	private String mobileNumber;

	private String otp;

}
