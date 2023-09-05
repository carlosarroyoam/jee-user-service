package com.carlosarroyoam.users.exception.mapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.carlosarroyoam.users.dto.APIErrorDto;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(PersistenceException exception) {
		APIErrorDto apiErrorDto = new APIErrorDto();
		Status status = Status.NOT_FOUND;

		if (exception instanceof EntityNotFoundException) {
			apiErrorDto.setMessage(exception.getMessage());
			apiErrorDto.setError(status.getReasonPhrase());
			apiErrorDto.setStatus(status.getStatusCode());
			apiErrorDto.setPath(uriInfo.getPath());
			apiErrorDto.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")).withFixedOffsetZone());

			return Response.status(status).entity(apiErrorDto).type(MediaType.APPLICATION_JSON).build();
		}

		throw exception;
	}

}
