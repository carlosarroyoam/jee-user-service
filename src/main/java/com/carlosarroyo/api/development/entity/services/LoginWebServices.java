/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.services;

import com.carlosarroyo.api.development.crypto.Authentication;
import com.carlosarroyo.api.development.entity.User;
import com.carlosarroyo.api.development.entity.dao.UserDAO;
import java.util.Objects;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 *
 * @author Carlos
 */
@Path("login")
public class LoginWebServices {
    
    private static final String LOGIN_STATUS_RESPONSE_TAG = "LOGIN_STATUS_RESPONSE";
    private static final int LOGIN_OK = 0;
    private static final int LOGIN_INVALID_EMAIL = 1111;
    private static final int LOGIN_INVALID_PASSWORD = 2222;
    
    private static final String MESSAGE_TAG = "MESSAGE";
    private static final String JSON_PARAMS_ERROR_TAG = "JSON_PARAMS_ERROR";
    

    @Context
    private UriInfo context;

    public LoginWebServices() {
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(String content) {
        JSONObject outputJSON = new JSONObject();
        String message = "";

        try {
            JSONParser jsonParser = new JSONParser();
            if (!content.equals("")) {
                JSONObject inputJSON = (JSONObject) jsonParser.parse(content);
                if (!inputJSON.isEmpty()) {

                    String email = inputJSON.get("email").toString().trim();
                    String password = inputJSON.get("password").toString().trim();

                    if (!email.equals("") && !password.equals("")) {
                        UserDAO userDAO = UserDAO.getUserDAOInstance();
                        User user = userDAO.getUserByEmail(email);

                        if (!Objects.equals(null, user.getEmail())) {
                            if (Authentication.verifyHash(password, user.getPassword())) {
                                outputJSON.put(LOGIN_STATUS_RESPONSE_TAG, LOGIN_OK);
                                outputJSON.put(MESSAGE_TAG, "Usuario auntenticado");

                            } else {
                                outputJSON.put(LOGIN_STATUS_RESPONSE_TAG, LOGIN_INVALID_PASSWORD);
                                outputJSON.put(MESSAGE_TAG, "La contraseña es incorrecta. Vuelve a intentarlo");
                            }
                            
                        } else {
                            outputJSON.put(LOGIN_STATUS_RESPONSE_TAG, LOGIN_INVALID_EMAIL);
                            outputJSON.put(MESSAGE_TAG, "No se ha encontrado ninguna cuenta con esa dirección de correo electrónico");
                        }
                        
                    } else {
                        outputJSON.put(JSON_PARAMS_ERROR_TAG, "Los campos no pueden ir vacios");
                    }

                } else {
                    outputJSON.put(JSON_PARAMS_ERROR_TAG, "El JSON recibido no contiene data");
                }

            } else {
                outputJSON.put(JSON_PARAMS_ERROR_TAG, "Se esperaba recibir un JSON con el email y password");
            }

        } catch (ParseException ex) {
            System.out.println("Login WS Error users/getUserById: " + ex.getMessage());

        }

        return outputJSON.toString();
    }

    @POST
    @Path("recoverPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String recoverPassword(String content) {
        JSONObject outputJSON = new JSONObject();
        String message = "Method under construction.";
        outputJSON.put("message", message);

        return outputJSON.toString();
    }

}
