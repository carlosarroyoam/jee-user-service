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

package com.carlosarroyoam.api.development.entity.services;

import com.carlosarroyoam.api.development.auth.Authentication;
import com.carlosarroyoam.api.development.entity.User;
import com.carlosarroyoam.api.development.entity.dao.UserDao;
import java.util.Objects;
import javax.ejb.Stateful;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 * This class handles all /user requests
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@Stateful
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserServices {

    /**
     * 
     * @return The list of users
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
     * @return A JSON with the requested user
     */
    @GET
    @Path(value = "/{id}")
    public Response getById(@PathParam("id") int id) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDao.getInstance().get(id))
                .build();
    }
    
    /**
     * 
     * @param email
     * @return A JSON with the requested user
     */
    @GET
    @Path("/byemail/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDao.getInstance().get(email))
                .build();
    }

    /**
     * 
     * @param requestBody
     * @return A JSON with the created user
     */
    @POST
    @Path("/")
    public Response create(String requestBody) {
        if (!requestBody.trim().equals("")) {
            JSONObject data = new JSONObject(requestBody);

            if (!data.isEmpty()) {
                User user = new User();
                user.setFirstName(data.getString("first_name"));
                user.setLastName(data.getString("last_name"));
                user.setEmail(data.getString("email"));
                user.setPassword(Authentication.passwordHash(data.getString("password")));

                User createdUser = UserDao.getInstance().create(user);
                if (!Objects.equals(null, createdUser.getEmail())) {
                    return Response.status(Response.Status.CREATED).entity(createdUser).build();
                }
                
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
}
