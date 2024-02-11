package com.carlosarroyoam.user.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class AppExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1982149359567491484L;
	private String message;
	private String error;
	private int status;
	private String path;
	private ZonedDateTime timestamp;

}
