package com.carlosarroyoam.user.service.exception.mapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.carlosarroyoam.user.service.dto.AppExceptionResponse;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(RuntimeException ex) {
		Status status = Status.INTERNAL_SERVER_ERROR;

		AppExceptionResponse appExceptionResponse = new AppExceptionResponse();
		appExceptionResponse.setMessage("Whoops! Something went wrong");
		appExceptionResponse.setCode(status.getStatusCode());
		appExceptionResponse.setStatus(status.getReasonPhrase());
		appExceptionResponse.setPath(uriInfo.getPath());
		appExceptionResponse.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")).withFixedOffsetZone());

		ex.printStackTrace();

		return Response.status(status).entity(appExceptionResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
