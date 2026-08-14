package com.dj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dj.entity.OtpVerification;

@Repository
public interface OtpVerificationRepo extends JpaRepository<OtpVerification, Long> {
	Optional<OtpVerification> findTopByMobileNumberOrderByCreatedAtDesc(String mobileNumber);
}
