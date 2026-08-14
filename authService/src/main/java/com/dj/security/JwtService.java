package com.dj.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET = "my-super-secret-key-for-auth-service-2026";

	private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	public String generateToken(String mobileNumber) {

		Date now = new Date();

		Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

		return Jwts.builder().subject(mobileNumber).issuedAt(now).expiration(expiry).signWith(secretKey).compact();
	}

	public String extractMobileNumber(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
	}
}