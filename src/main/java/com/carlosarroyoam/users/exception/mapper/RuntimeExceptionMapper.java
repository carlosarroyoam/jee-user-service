package com.carlosarroyoam.users.exception.mapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.carlosarroyoam.users.dto.APIErrorDto;

/**
 * An {@link ExceptionMapper} implementation for all {@link RuntimeException}s.
 */
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(RuntimeException exception) {
		APIErrorDto apiErrorDto = new APIErrorDto();
		Status status = Status.INTERNAL_SERVER_ERROR;

		apiErrorDto.setMessage(
				"The server encountered an unexpected condition that prevents it from completing the request");
		apiErrorDto.setError(status.getReasonPhrase());
		apiErrorDto.setStatus(status.getStatusCode());
		apiErrorDto.setPath(uriInfo.getPath());
		apiErrorDto.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")).withFixedOffsetZone());

		exception.printStackTrace();

		return Response.status(status).entity(apiErrorDto).type(MediaType.APPLICATION_JSON).build();
	}
}
