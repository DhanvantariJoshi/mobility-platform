package com.dj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dj.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByMobileNumber(String mobileNumber);

	Optional<User> findByEmail(String email);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);
}
