package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
  @Context
  private UriInfo uriInfo;

  @Override
  public Response toResponse(WebApplicationException ex) {
    Status status = Status.fromStatusCode(ex.getResponse().getStatus());

    AppExceptionResponse appExceptionResponse = AppExceptionResponse.builder()
        .message(ex.getMessage())
        .code(status.getStatusCode())
        .status(status.getReasonPhrase())
        .path(uriInfo.getPath())
        .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
        .build();

    return Response.status(status)
        .entity(appExceptionResponse)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
