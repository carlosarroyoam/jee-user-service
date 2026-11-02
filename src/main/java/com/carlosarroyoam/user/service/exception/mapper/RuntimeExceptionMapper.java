package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionDto;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
