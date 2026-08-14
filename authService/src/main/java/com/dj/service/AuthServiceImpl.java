package com.dj.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dj.dto.LoginRequest;
import com.dj.dto.LoginResponse;
import com.dj.dto.OtpResponse;
import com.dj.dto.RegisterRequest;
import com.dj.dto.RegisterResponse;
import com.dj.dto.SendOtpRequest;
import com.dj.dto.VerifyOtpRequest;
import com.dj.entity.OtpVerification;
import com.dj.entity.User;
import com.dj.repository.OtpVerificationRepo;
import com.dj.repository.UserRepository;
import com.dj.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

	private final OtpVerificationRepo otpVerificationRepo;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	public OtpResponse sendOtp(SendOtpRequest request) {

		String mobileNumber = request.getMobileNumber();

		// Generate 6 digit OTP
		String otp = String.valueOf(100000 + new Random().nextInt(900000));

		OtpVerification otpVerification = OtpVerification.builder().mobileNumber(mobileNumber).otp(otp)
				.expiryTime(LocalDateTime.now().plusMinutes(5)).verified(false).attempts(0)
				.createdAt(LocalDateTime.now()).build();

		otpVerificationRepo.save(otpVerification);

		// Temporary local testing
		System.out.println("OTP for " + mobileNumber + " = " + otp);

		return OtpResponse.builder().message("OTP sent successfully").verified(false).build();
	}

	public OtpResponse verifyOtp(VerifyOtpRequest request) {

		OtpVerification otpVerification = otpVerificationRepo
				.findTopByMobileNumberOrderByCreatedAtDesc(request.getMobileNumber())
				.orElseThrow(() -> new RuntimeException("OTP not found"));

		// Check expiry
		if (otpVerification.getExpiryTime().isBefore(LocalDateTime.now())) {

			throw new RuntimeException("OTP expired");
		}

		// Check OTP
		if (!otpVerification.getOtp().equals(request.getOtp())) {

			otpVerification.setAttempts(otpVerification.getAttempts() + 1);

			otpVerificationRepo.save(otpVerification);

			throw new RuntimeException("Invalid OTP");
		}

		otpVerification.setVerified(true);

		otpVerificationRepo.save(otpVerification);

		return OtpResponse.builder().message("Mobile number verified successfully").verified(true).build();
	}

	public RegisterResponse register(RegisterRequest request) {

		if (userRepository.existsByMobileNumber(request.getMobileNumber())) {

			throw new RuntimeException("Mobile number already registered");
		}

		if (request.getEmail() != null && userRepository.existsByEmail(request.getEmail())) {

			throw new RuntimeException("Email already registered");
		}

		OtpVerification verification = otpVerificationRepo
				.findTopByMobileNumberOrderByCreatedAtDesc(request.getMobileNumber())
				.orElseThrow(() -> new RuntimeException("Mobile number is not verified"));

		if (!verification.getVerified()) {
			throw new RuntimeException("Please verify your mobile number first");
		}

		User user = User.builder().mobileNumber(request.getMobileNumber()).firstName(request.getFirstName())
				.lastName(request.getLastName()).email(request.getEmail()).role(request.getRole())
				.password(passwordEncoder.encode(request.getPassword())).active(true).build();

		User savedUser = userRepository.save(user);

		return RegisterResponse.builder().id(savedUser.getId()).mobileNumber(savedUser.getMobileNumber())
				.firstName(savedUser.getFirstName()).lastName(savedUser.getLastName()).email(savedUser.getEmail())
				.message("Registration successful").build();
	}

	public LoginResponse login(LoginRequest request) {

		User user = userRepository.findByMobileNumber(request.getMobileNumber())
				.orElseThrow(() -> new RuntimeException("Invalid mobile number or password"));

		if (!user.getActive()) {
			throw new RuntimeException("User account is inactive");
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

			throw new RuntimeException("Invalid mobile number or password");
		}

		String token = jwtService.generateToken(user.getMobileNumber());

		return LoginResponse.builder().userId(user.getId()).mobileNumber(user.getMobileNumber())
				.role(user.getRole().name()).token(token).message("Login successful").build();
	}
}