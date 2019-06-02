/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String remember_token;
    private String api_token;
    private Timestamp create_at;
    private Timestamp updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberToken() {
        return remember_token;
    }

    public void setRememberToken(String remember_token) {
        this.remember_token = remember_token;
    }

    public String getApiToken() {
        return api_token;
    }

    public void setApiToken(String api_token) {
        this.api_token = api_token;
    }

    public Timestamp getCreateAt() {
        return create_at;
    }

    public void setCreateAt(Timestamp create_at) {
        this.create_at = create_at;
    }

    public String getCreatedAtStringFormatted() {
        if(Objects.equals(null, create_at))
            return "";
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        String formattedDate = simpleDateFormat.format(create_at);
        return formattedDate;
    }

    public Timestamp getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdatedAtStringFormatted() {
        if(Objects.equals(null, updated_at))
            return "";
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        String formattedDate = simpleDateFormat.format(updated_at);
        return formattedDate;
    }
}
