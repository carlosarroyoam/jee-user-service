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

import com.carlosarroyoam.api.auth.Authentication;
import com.carlosarroyoam.api.models.User;
import com.carlosarroyoam.api.dao.UserDao;
import java.util.Optional;
import javax.ejb.Stateful;
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
import org.json.JSONObject;

/**
 * This class handles all example-domain.com/user requests.
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@Stateful
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    /**
     *
     * @return The list of users.
     */
    @GET
    @Path("/")
    public Response getAll() {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDao.getInstance().getAll())
                .build();
    }

    /**
     *
     * @param id
     * @return The requested User.
     */
    @GET
    @Path(value = "/{id}")
    public Response getById(@PathParam("id") int id) {
        Optional<User> user = UserDao.getInstance().get(id);

        if (user.isPresent()) {
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(user.get())
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("NOT_FOUND")
                    .build();
        }
    }

    /**
     *
     * @param email
     * @return The requested User.
     */
    @GET
    @Path("/byemail/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        Optional<User> user = UserDao.getInstance().get(email);

        if (user.isPresent()) {
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(user.get())
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("NOT_FOUND")
                    .build();
        }
    }

    /**
     *
     * @param requestBody
     * @return The stored User.
     */
    @POST
    @Path("/")
    public Response store(String requestBody) {
        if (!requestBody.trim().equals("")) {
            JSONObject data = new JSONObject(requestBody);

            if (!data.isEmpty()) {
                User user = new User();
                user.setFirstName(data.getString("first_name"));
                user.setLastName(data.getString("last_name"));
                user.setEmail(data.getString("email"));
                user.setPassword(Authentication.passwordHash(data.getString("password")));

                Optional<User> createdUser = UserDao.getInstance().create(user);

                if (createdUser.isPresent()) {
                    return Response.status(Response.Status.CREATED)
                            .type(MediaType.APPLICATION_JSON)
                            .entity(createdUser.get())
                            .build();
                }

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("INTERNAL_SERVER_ERROR")
                        .build();
            }
        }
        
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity("BAD_REQUEST")
                .build();
    }
    
    /**
     *
     * @param id
     * @return The updated User.
     */
    @PUT
    @Path(value = "/{id}")
    public Response update(@PathParam("id") int id) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .type(MediaType.APPLICATION_JSON)
                .entity("SERVICE_UNAVAILABLE")
                .build();
    }

    /**
     *
     * @param id
     * @return A boolean, indicating if user was successfully deleted or not.
     */
    @DELETE
    @Path(value = "/{id}")
    public Response destroy(@PathParam("id") int id) {
        Optional<User> userToDelete = UserDao.getInstance().get(id);

        if (userToDelete.isPresent()) {
            if (UserDao.getInstance().delete(userToDelete.get())) {
                return Response.status(Response.Status.OK)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("deleted")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .type(MediaType.APPLICATION_JSON)
                        .entity("INTERNAL_SERVER_ERROR")
                        .build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity("NOT_FOUND")
                .build();
    }

}
