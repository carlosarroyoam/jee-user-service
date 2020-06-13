/*
 * The MIT License
 *
 * Copyright 2019 Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.carlosarroyoam.api.controllers;

import com.carlosarroyoam.api.models.User;
import com.carlosarroyoam.api.services.UserService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class handles all example-domain.com/user requests.
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;
    
    public UserController() {
        this.userService = UserService.getInstance();
    }
    
    /**
     *
     * @return The list of users.
     */
    @GET
    @Path("")
    public Response index() {
        return Response.status(Response.Status.OK)
                .entity(userService.findAll())
                .build();
    }

    /**
     *
     * @param id The User id to be requested.
     * @return The requested User.
     */
    @GET
    @Path(value ="{id}")
    public Response show(@PathParam("id") int id) {
        return Response.status(Response.Status.OK)
                .entity(userService.findById(id).get())
                .build();
    }

    /**
     *
     * @param email The User email to be requested.
     * @return The requested User.
     */
    @GET
    @Path("byemail/{email}")
    public Response showByEmail(@PathParam("email") String email) {
        return Response.status(Response.Status.OK)
                .entity(userService.findByEmail(email).get())
                .build();
    }

    /**
     *
     * @param user
     * @return The stored User.
     */
    @POST
    @Path("")
    public Response store(User user) {
        return Response.status(Response.Status.CREATED)
                .entity(userService.save(user).get())
                .build();
    }

    /**
     *
     * @param id The User id to be updated.
     * @param user The User to be updated.
     * @return The updated User.
     */
    @PUT
    @Path(value = "{id}")
    public Response update(@PathParam("id") int id, User user) {
        user.setId(id);
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(userService.save(user).get())
                .build();
    }

    /**
     *
     * @param id The User id to be deleted.
     * @return A boolean, indicating if user was successfully deleted or not.
     */
    @DELETE
    @Path(value = "{id}")
    public Response destroy(@PathParam("id") int id) {
        boolean userToDelete = userService.delete(id);

        return Response.status(Response.Status.OK)
                .entity(userToDelete)
                .build();
    }

}
