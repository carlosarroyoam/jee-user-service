package com.carlosarroyoam.user.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private String name;
	private Byte age;
	private String email;
	private String username;
	private String role;
	private Boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
