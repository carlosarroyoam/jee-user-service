/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.entity.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@Path("notifications")
public class NotificationServices {

    /**
     * 
     * @param requestBody
     * @return Response
     */
    @POST
    @Path("getByUserId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUserId(String requestBody) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }
}
