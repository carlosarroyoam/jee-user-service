package com.carlosarroyoam.user.service.resource;

import com.carlosarroyoam.user.service.dto.CreateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UpdateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UserDto;
import com.carlosarroyoam.user.service.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

/** Expone los endpoints REST para administrar usuarios bajo {@code /users}. */
@Path("/users")
@ApplicationScoped
public class UserResource {
  @Inject private UserService userService;

  /**
   * Obtiene todos los usuarios mediante {@code GET /users}.
   *
   * @return la lista de {@link UserDto} y el estado 200 OK
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAll() {
    List<UserDto> users = userService.findAll();
    return Response.ok(users).build();
  }

  /**
   * Busca un usuario por su id mediante {@code GET /users/{userId}}.
   *
   * @param userId el id del usuario
   * @return el {@link UserDto} encontrado y el estado 200 OK
   */
  @GET
  @Path("/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findById(@PathParam("userId") Long userId) {
    UserDto userById = userService.findById(userId);
    return Response.ok(userById).build();
  }

  /**
   * Crea un usuario mediante {@code POST /users}.
   *
   * @param requestDto los datos del usuario a crear
   * @return el estado 201 Created con la cabecera {@code Location} del usuario creado
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(@Valid CreateUserRequestDto requestDto) {
    UserDto createdUser = userService.create(requestDto);
    URI locationUri =
        UriBuilder.fromResource(UserResource.class)
            .path("/{userId}")
            .resolveTemplate("userId", createdUser.getId())
            .build();
    return Response.created(locationUri).build();
  }

  /**
   * Actualiza un usuario mediante {@code PUT /users/{userId}}.
   *
   * @param userId el id del usuario a actualizar
   * @param requestDto los nuevos datos del usuario
   * @return el estado 204 No Content
   */
  @PUT
  @Path("/{userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("userId") Long userId, @Valid UpdateUserRequestDto requestDto) {
    userService.update(userId, requestDto);
    return Response.noContent().build();
  }

  /**
   * Elimina un usuario mediante {@code DELETE /users/{userId}}.
   *
   * @param userId el id del usuario a eliminar
   * @return el estado 204 No Content
   */
  @DELETE
  @Path("/{userId}")
  public Response deleteById(@PathParam("userId") Long userId) {
    userService.deleteById(userId);
    return Response.noContent().build();
  }
}
