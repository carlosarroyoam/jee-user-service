/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.database;

/**
 * 
 * @author Carlos Alberto Arroyo Martinez <carlosarroyoam@gmail.com>
 */
public class DatabaseSchema {

    public static final String DATABASENAME = "development";

    public static final class UsersTable {

        public static final String TABLENAME = "users";

        public static final class Cols {

            public static final String UUID = "id";
            public static final String FIRSTNAME = "first_name";
            public static final String LASTNAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String REMEMBERTOKEN = "remember_token";
            public static final String CREATEDAT = "created_at";
            public static final String UPDATEDAT = "updated_at";
        }
    }
}
