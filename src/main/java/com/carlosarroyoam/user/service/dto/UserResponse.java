package com.carlosarroyoam.user.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private String name;
	private Integer age;
	private String email;
	private String username;
	private String role;
	private Boolean isActive;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;

}
