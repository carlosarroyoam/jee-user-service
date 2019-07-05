/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.database;

/**
 * This class provides schema layout for all database tables as well as global
 * constants related to database
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DatabaseSchema {

    public static final String DATABASE_NAME = "development";

    public static class UsersTable {

        public static final String TABLE_NAME = "users";

        public static class Cols {

            public static final String UUID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String REMEMBER_TOKEN = "remember_token";
            public static final String CREATED_AT = "created_at";
            public static final String UPDATED_AT = "updated_at";
        }
    }
}
