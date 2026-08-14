package com.dj.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	private String lastName;

	@Column(nullable = false, unique = true)
	private String mobileNumber;

	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column(nullable = false)
	private Boolean active;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@PrePersist
	public void onCreate() {

		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();

		if (active == null) {
			active = true;
		}
	}

	@PreUpdate
	public void onUpdate() {

		updatedAt = LocalDateTime.now();
	}

}
