/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.services;

import com.carlosarroyo.api.development.entity.User;
import com.carlosarroyo.api.development.entity.dao.UserDAO;
import java.util.ArrayList;
import javax.ejb.Stateful;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
@Stateful
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserServices {

    @Context
    private UriInfo context;

    @GET
    @Path("/")
    public Response getAll() {
        GenericEntity<ArrayList<User>> list = new GenericEntity<ArrayList<User>> (UserDAO.getInstance().getAll()) {}; 
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(list)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDAO.getInstance().getById(id))
                .build();
    }

    @GET
    @Path("/byemail/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(UserDAO.getInstance().getByEmail(email))
                .build();
    }
}
