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
 * This class handles all /authentication requests
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
@Stateful
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("authentication")
public class UserAuthenticationServices {

    /**
     * 
     * @param requestBody
     * @return The authenticated user info
     */
    @POST
    @Path("authenticate")
    public Response getToken(String requestBody) {
        if (!requestBody.trim().equals("")) {
            JSONObject data = new JSONObject(requestBody);
            
            if (!data.isEmpty()) {
                String email = data.get("email").toString();
                String password = data.get("password").toString();

                if (!email.trim().equals("") && !password.trim().equals("")) {
                    User user = UserDao.getInstance().get(email);
                    if (!Objects.equals(null, user.getEmail())) {
                        if (Authentication.verifyHash(password, user.getPassword())) {
                            return Response.status(Response.Status.OK).entity(user).build();
                        }
                        return Response.status(Response.Status.UNAUTHORIZED).build();
                    }
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * 
     * @param requestBody
     * @return 
     */
    @POST
    @Path("passwordReset")
    public Response recoverPassword(String requestBody) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }

    /**
     * 
     * @param requestBody
     * @return The encrypted password hash string
     */
    @POST
    @Path("passwordEncrypt")
    public Response encryptPassword(String requestBody) {
        if (!requestBody.trim().equals("")) {
            JSONObject data = new JSONObject(requestBody);

            if (!data.isEmpty()) {
                Map messagesList = new HashMap();
                messagesList.put("passwordHash", Authentication.passwordHash(data.get("password").toString()));
                return Response.status(Response.Status.OK).entity(messagesList).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
