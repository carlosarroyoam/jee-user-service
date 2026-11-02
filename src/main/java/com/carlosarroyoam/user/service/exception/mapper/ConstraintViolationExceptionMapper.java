package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionDto;
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

/**
 * Traduce las {@link ConstraintViolationException} de validación a una respuesta 400 Bad Request
 * con un {@link AppExceptionDto}.
 */
@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {
  @Context private UriInfo uriInfo;

  /**
   * Construye la respuesta de error con los mensajes de las restricciones incumplidas.
   *
   * @param ex la excepción de validación lanzada
   * @return la respuesta con el estado 400 Bad Request y el detalle de cada violación
   */
  @Override
  public Response toResponse(ConstraintViolationException ex) {
    Status status = Status.BAD_REQUEST;

    AppExceptionDto appExceptionDto =
        AppExceptionDto.builder()
            .message("Request data is not valid")
            .code(status.getStatusCode())
            .status(status.getReasonPhrase())
            .path(uriInfo.getPath())
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
            .details(
                ex.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet()))
            .build();

    return Response.status(status).entity(appExceptionDto).type(MediaType.APPLICATION_JSON).build();
  }
}
