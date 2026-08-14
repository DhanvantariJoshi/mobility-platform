package com.dj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dj.dto.OtpResponse;
import com.dj.dto.SendOtpRequest;
import com.dj.dto.VerifyOtpRequest;
import com.dj.service.AuthServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
	
	private final AuthServiceImpl authService;

    @PostMapping("/send-otp")
    public ResponseEntity<OtpResponse> sendOtp(
            @RequestBody SendOtpRequest request) {

        return ResponseEntity.ok(
                authService.sendOtp(request)
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponse> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
                authService.verifyOtp(request)
        );
    }

}
