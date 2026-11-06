package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Traduce cualquier {@link RuntimeException} no controlada a una respuesta 500 Internal Server
 * Error con un {@link AppExceptionDto}.
 */
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
  @Inject private Logger logger;
  @Context private UriInfo uriInfo;

  /**
   * Registra la excepción y construye una respuesta de error genérica.
   *
   * @param ex la excepción no controlada
   * @return la respuesta con el estado 500 Internal Server Error
   */
  @Override
  public Response toResponse(RuntimeException ex) {
    Status status = Status.INTERNAL_SERVER_ERROR;

    AppExceptionDto appExceptionDto =
        AppExceptionDto.builder()
            .message("Whoops! Something went wrong")
            .code(status.getStatusCode())
            .status(status.getReasonPhrase())
            .path(uriInfo.getPath())
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
            .build();

    logger.log(Level.SEVERE, "Whoops! Something went wrong", ex);

    return Response.status(status).entity(appExceptionDto).type(MediaType.APPLICATION_JSON).build();
  }
}
