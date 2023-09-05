package com.carlosarroyoam.users.dto;

import java.time.ZonedDateTime;

import javax.json.bind.annotation.JsonbPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonbPropertyOrder({ "message", "error", "status", "path", "timestamp" })
public class APIErrorDto {

	private String message;
	private String error;
	private int status;
	private String path;
	private ZonedDateTime timestamp;

}
