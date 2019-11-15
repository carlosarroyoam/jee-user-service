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
import com.carlosarroyoam.api.services.AuthService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class handles all example-domain.com/authentication requests.
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("authentication")
public class AuthenticationController {

    private final AuthService authService;
    
    public AuthenticationController() {
        this.authService = AuthService.getInstance();
    }
    
    /**
     * Authenticates a user.
     * 
     * @param user The User to be authenticated.
     * @return The authenticated User.
     */
    @POST
    @Path("auth")
    public Response getToken(User user) {
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(authService.auth(user).get())
                .build();
    }

    /**
     * Resets user password.
     * 
     * @return
     */
    @POST
    @Path("passwordReset")
    public Response recoverPassword() {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .type(MediaType.APPLICATION_JSON)
                .entity("SERVICE_UNAVAILABLE")
                .build();
    }

}
