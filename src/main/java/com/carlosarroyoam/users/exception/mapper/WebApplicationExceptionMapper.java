package com.carlosarroyoam.users.exception.mapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.carlosarroyoam.users.dto.APIErrorDto;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

	@Context
	private UriInfo uriInfo;

	@Override
	public Response toResponse(WebApplicationException exception) {
		APIErrorDto apiErrorDto = new APIErrorDto();

		apiErrorDto.setMessage(exception.getMessage());
		apiErrorDto.setError(exception.getResponse().getStatusInfo().getReasonPhrase());
		apiErrorDto.setStatus(exception.getResponse().getStatusInfo().getStatusCode());
		apiErrorDto.setPath(uriInfo.getPath());
		apiErrorDto.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")).withFixedOffsetZone());

		return Response.status(exception.getResponse().getStatusInfo().getStatusCode()).entity(apiErrorDto)
				.type(MediaType.APPLICATION_JSON).build();
	}

}
