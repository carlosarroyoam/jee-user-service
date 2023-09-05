package com.carlosarroyoam.users.controller;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

import com.carlosarroyoam.users.entity.User;
import com.carlosarroyoam.users.service.UserService;

@Path("/users")
@ApplicationScoped
public class UserController {

	@Inject
	private UserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(userService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(userService.findById(id)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(User user) {
		userService.create(user);

		URI location = UriBuilder.fromResource(UserController.class).path("/{id}").resolveTemplate("id", user.getId())
				.build();

		return Response.created(location).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, User user) {
		return Response.ok(userService.update(id, user)).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		userService.delete(id);
		return Response.ok().build();
	}

}
