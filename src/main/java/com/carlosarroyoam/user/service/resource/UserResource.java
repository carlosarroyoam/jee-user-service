package com.carlosarroyoam.user.service.resource;

import com.carlosarroyoam.user.service.dto.CreateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UpdateUserRequestDto;
import com.carlosarroyoam.user.service.dto.UserDto;
import com.carlosarroyoam.user.service.service.UserService;
import java.net.URI;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/users")
@ApplicationScoped
public class UserResource {
  @Inject
  private UserService userService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response findAll() {
    List<UserDto> users = userService.findAll();
    return Response.ok(users).build();
  }

  @GET
  @Path("/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findById(@PathParam("userId") Long userId) {
    UserDto userById = userService.findById(userId);
    return Response.ok(userById).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response create(@Valid CreateUserRequestDto requestDto) {
    UserDto createdUser = userService.create(requestDto);
    URI locationUri = UriBuilder.fromResource(UserResource.class)
        .path("/{userId}")
        .resolveTemplate("userId", createdUser.getId())
        .build();
    return Response.created(locationUri).build();
  }

  @PUT
  @Path("/{userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("userId") Long userId, @Valid UpdateUserRequestDto requestDto) {
    userService.update(userId, requestDto);
    return Response.noContent().build();
  }

  @DELETE
  @Path("/{userId}")
  public Response deleteById(@PathParam("userId") Long userId) {
    userService.deleteById(userId);
    return Response.noContent().build();
  }
}
