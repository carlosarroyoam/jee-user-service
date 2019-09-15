/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.entity.services;

import com.carlosarroyoam.api.development.auth.Authentication;
import com.carlosarroyoam.api.development.entity.User;
import com.carlosarroyoam.api.development.entity.dao.UserDAO;
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
     * @return Response
     */
    @GET
    @Path("/")
    public Response getAll() {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDAO.getInstance().getAll())
                .build();
    }

    /**
     * 
     * @param id
     * @return Response
     */
    @GET
    @Path(value = "/{id}")
    public Response getById(@PathParam("id") int id) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDAO.getInstance().get(id))
                .build();
    }
    
    /**
     * 
     * @param email
     * @return Response
     */
    @GET
    @Path("/byemail/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDAO.getInstance().get(email))
                .build();
    }

    /**
     * 
     * @param requestBody
     * @return Response
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

                User createdUser = UserDAO.getInstance().create(user);
                if (!Objects.equals(null, createdUser.getEmail())) {
                    return Response.status(Response.Status.CREATED).entity(createdUser).build();
                }
                
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
}
