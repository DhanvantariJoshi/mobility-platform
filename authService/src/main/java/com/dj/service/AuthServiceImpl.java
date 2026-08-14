package com.dj.service;




import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dj.dto.OtpResponse;
import com.dj.dto.SendOtpRequest;
import com.dj.dto.VerifyOtpRequest;
import com.dj.entity.OtpVerification;
import com.dj.repository.OtpVerificationRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl{

    private final OtpVerificationRepo otpVerificationRepo;

    public OtpResponse sendOtp(SendOtpRequest request) {

        String mobileNumber = request.getMobileNumber();

        // Generate 6 digit OTP
        String otp = String.valueOf(
                100000 + new Random().nextInt(900000)
        );

        OtpVerification otpVerification = OtpVerification.builder()
                .mobileNumber(mobileNumber)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .verified(false)
                .attempts(0)
                .createdAt(LocalDateTime.now())
                .build();

        otpVerificationRepo.save(otpVerification);

        // Temporary local testing
        System.out.println(
                "OTP for " + mobileNumber + " = " + otp
        );

        return OtpResponse.builder()
                .message("OTP sent successfully")
                .verified(false)
                .build();
    }

    public OtpResponse verifyOtp(VerifyOtpRequest request) {

        OtpVerification otpVerification =
                otpVerificationRepo
                        .findTopByMobileNumberOrderByCreatedAtDesc(
                                request.getMobileNumber()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "OTP not found"
                                )
                        );

        // Check expiry
        if (otpVerification.getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("OTP expired");
        }

        // Check OTP
        if (!otpVerification.getOtp()
                .equals(request.getOtp())) {

            otpVerification.setAttempts(
                    otpVerification.getAttempts() + 1
            );

            otpVerificationRepo.save(otpVerification);

            throw new RuntimeException("Invalid OTP");
        }

        otpVerification.setVerified(true);

        otpVerificationRepo.save(otpVerification);

        return OtpResponse.builder()
                .message("Mobile number verified successfully")
                .verified(true)
                .build();
    }
}