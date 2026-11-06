package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionDto;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Traduce las {@link WebApplicationException} (por ejemplo 400 y 404) a una respuesta con un {@link
 * AppExceptionDto} que conserva el estado original.
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
  @Context private UriInfo uriInfo;

  /**
   * Construye la respuesta de error con el mensaje y el estado de la excepción.
   *
   * @param ex la excepción lanzada
   * @return la respuesta con el mismo estado HTTP que el de la excepción
   */
  @Override
  public Response toResponse(WebApplicationException ex) {
    Status status = Status.fromStatusCode(ex.getResponse().getStatus());

    AppExceptionDto appExceptionDto =
        AppExceptionDto.builder()
            .message(ex.getMessage())
            .code(status.getStatusCode())
            .status(status.getReasonPhrase())
            .path(uriInfo.getPath())
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
            .build();

    return Response.status(status).entity(appExceptionDto).type(MediaType.APPLICATION_JSON).build();
  }
}
