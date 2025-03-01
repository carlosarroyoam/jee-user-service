package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {
  @Context
  private UriInfo uriInfo;

  @Override
  public Response toResponse(ConstraintViolationException ex) {
    Status status = Status.BAD_REQUEST;

    AppExceptionResponse appExceptionResponse = new AppExceptionResponse();
    appExceptionResponse.setMessage("Request data is not valid");
    appExceptionResponse.setCode(status.getStatusCode());
    appExceptionResponse.setStatus(status.getReasonPhrase());
    appExceptionResponse.setPath(uriInfo.getPath());
    appExceptionResponse.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")));
    appExceptionResponse.setDetails(ex.getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toSet()));

    return Response.status(status)
        .entity(appExceptionResponse)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
