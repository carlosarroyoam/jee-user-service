package com.carlosarroyoam.user.service.exception.mapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.carlosarroyoam.user.service.dto.AppExceptionResponse;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(ValidationException exception) {
		AppExceptionResponse apiErrorDto = new AppExceptionResponse();
		Status status = Status.BAD_REQUEST;

		apiErrorDto.setMessage(exception.getMessage());
		apiErrorDto.setError(status.getReasonPhrase());
		apiErrorDto.setStatus(status.getStatusCode());
		apiErrorDto.setPath(uriInfo.getPath());
		apiErrorDto.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")).withFixedOffsetZone());

		exception.printStackTrace();

		return Response.status(status).entity(apiErrorDto).type(MediaType.APPLICATION_JSON).build();
	}

}
