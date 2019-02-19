/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.services;

import com.carlosarroyo.api.development.database.DatabaseSchema;
import com.carlosarroyo.api.development.entity.User;
import com.carlosarroyo.api.development.entity.dao.UserDAO;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 * 
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
@Path("users")
public class UserServices {

    @Context
    private UriInfo context;

    public UserServices() {
    }

    @POST
    @Path("getUserById")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserById(String content) {
        JSONObject outputJSON = new JSONObject();
        String message = "";
        try {
            JSONParser jsonParser = new JSONParser();

            if (!content.equals("")) {
                JSONObject inputJSON = (JSONObject) jsonParser.parse(content);
                String id_user = inputJSON.get("id").toString();
                UserDAO userDAO = UserDAO.getInstance();
                User user = userDAO.getUserById(Integer.parseInt(id_user));

                outputJSON.put(DatabaseSchema.UsersTable.Cols.UUID, user.getIdUser());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.NAME, user.getName());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.LASTNAME, user.getLastname());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.EMAIL, user.getEmail());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.CREATEDATE, user.getCreatedate());

            } else {
                outputJSON.put("state", "1");
                outputJSON.put("message", "La solicitud esperaba un JSON.");
            }

        } catch (ParseException ex) {
            System.out.println("WS Error users/getUserById: " + ex.getMessage());

        }

        return outputJSON.toString();
    }

    @POST
    @Path("getUserByEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserByEmail(String content) {
        JSONObject outputJSON = new JSONObject();
        String message = "";
        try {
            JSONParser jsonParser = new JSONParser();

            if (!content.equals("")) {
                JSONObject inputJSON = (JSONObject) jsonParser.parse(content);
                String email = inputJSON.get("email").toString();
                UserDAO userDAO = UserDAO.getInstance();
                User user = userDAO.getUserByEmail(email);

                outputJSON.put(DatabaseSchema.UsersTable.Cols.UUID, user.getIdUser());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.NAME, user.getName());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.LASTNAME, user.getLastname());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.EMAIL, user.getEmail());
                outputJSON.put(DatabaseSchema.UsersTable.Cols.CREATEDATE, user.getCreatedate());

            } else {
                outputJSON.put("message", "Se esperaba un JSON");
            }

        } catch (ParseException ex) {
            System.out.println("WS Error users/getUserById: " + ex.getMessage());

        }

        return outputJSON.toString();
    }

    @GET
    @Path("getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        JSONArray outputJSONArray = new JSONArray();
        String message = "";

        UserDAO userDAO = UserDAO.getInstance();
        ArrayList<User> usersArrayList = userDAO.getAllUsers();

        for (User user : usersArrayList) {
            JSONObject userJSONObject = new JSONObject();

            userJSONObject.put(DatabaseSchema.UsersTable.Cols.UUID, user.getIdUser());
            userJSONObject.put(DatabaseSchema.UsersTable.Cols.NAME, user.getName());
            userJSONObject.put(DatabaseSchema.UsersTable.Cols.LASTNAME, user.getLastname());
            userJSONObject.put(DatabaseSchema.UsersTable.Cols.EMAIL, user.getEmail());
            userJSONObject.put(DatabaseSchema.UsersTable.Cols.CREATEDATE, user.getCreatedate());

            outputJSONArray.add(userJSONObject);
        }

        return outputJSONArray.toString();
    }

}
