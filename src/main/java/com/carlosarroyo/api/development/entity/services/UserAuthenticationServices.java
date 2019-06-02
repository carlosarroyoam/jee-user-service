/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.services;

import com.carlosarroyo.api.development.crypto.Authentication;
import com.carlosarroyo.api.development.entity.User;
import com.carlosarroyo.api.development.entity.dao.UserDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateful;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
@Stateful
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("authentication")
public class UserAuthenticationServices {

    @POST
    @Path("authenticate")
    public Response getToken(String requestBody) {
        if (!requestBody.trim().equals("")) {
            JSONObject data = new JSONObject(requestBody);
            
            if (!data.isEmpty()) {
                String email = data.get("email").toString().trim();
                String password = data.get("password").toString().trim();

                if (!email.equals("") && !password.equals("")) {
                    User user = UserDAO.getInstance().get(email);
                    if (!Objects.equals(null, user.getEmail())) {
                        if (Authentication.verifyHash(password, user.getPassword())) {
                            return Response.status(Response.Status.OK).entity(user).build();
                        }
                        return Response.status(Response.Status.UNAUTHORIZED).build();
                    }
                }
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("passwordReset")
    public Response recoverPassword(String content) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }

    @POST
    @Path("passwordEncrypt")
    public Response encryptPassword(String content) {
        if (!content.trim().equals("")) {
            JSONObject data = new JSONObject(content);

            if (!data.isEmpty()) {
                Map messagesList = new HashMap();
                messagesList.put("passwordHash", Authentication.passwordHash(data.get("password").toString()));
                return Response.status(Response.Status.OK).entity(messagesList).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
