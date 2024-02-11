package com.carlosarroyoam.user.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class APIErrorDto {

	private String message;
	private String error;
	private int status;
	private String path;
	private ZonedDateTime timestamp;

}
