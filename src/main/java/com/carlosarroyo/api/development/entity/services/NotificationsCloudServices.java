/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
@Path("notificationscloud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsCloudServices {

    @POST
    @Path("send")
    public Response login(String content) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }

    private void sendMessageOverSocket(String message) {

    }
}
