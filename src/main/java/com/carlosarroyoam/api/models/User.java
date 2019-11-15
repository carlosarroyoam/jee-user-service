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

package com.carlosarroyoam.api.models;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Objects;

/**
 * User entity
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */

public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Timestamp created_at;
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

    public Timestamp getCreateAt() {
        return created_at;
    }

    public void setCreateAt(Timestamp create_at) {
        this.created_at = create_at;
    }

    public String getCreatedAtStringFormatted() {
        if(Objects.equals(null, created_at))
            return "";
        
        return dateToString(created_at);
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
        
        return dateToString(updated_at);
    }
    
    private String dateToString(Timestamp date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", new Locale("es", "MX"));
        return simpleDateFormat.format(date);
    }
}
