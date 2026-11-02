package com.carlosarroyoam.user.service.exception.mapper;

import com.carlosarroyoam.user.service.dto.AppExceptionDto;
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
