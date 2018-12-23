/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Carlos
 */
public class User {
    
    private int id_user;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Date createdate;
    

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Date getCreatedate() {
        return createdate;
    }
    
    public String getCreatedateStringFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        String formattedDate = simpleDateFormat.format(createdate);
        return formattedDate;
    }

    public void setCreatedate(String Date) throws ParseException {
        Date dateFormattedCreatedate = new SimpleDateFormat("dd/MM/yyyy h:mm a").parse(Date);
        this.createdate = dateFormattedCreatedate;
    }
    
    
}
