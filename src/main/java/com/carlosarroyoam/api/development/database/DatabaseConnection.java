/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains methods for managing database connections
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class DatabaseConnection {

    private static final String DATABASE_HOST = "localhost";
    private static final String DATABASE_PORT = "3306";
    private static final String DATABASE_NAME = DatabaseSchema.DATABASE_NAME;
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "toor";
    private Connection connection = null;
    
    private static DatabaseConnection DatabaseConeConnectionInstance;
    
    /**
    * Gets this class instance, avoids to have multiple DatabaseConnection
    * class instances
    *
    * @return DatabaseConnection instance of this class
    */
    public static DatabaseConnection getInstance() {
        if (DatabaseConeConnectionInstance == null) {
            DatabaseConeConnectionInstance = new DatabaseConnection();
        }

        return DatabaseConeConnectionInstance;
    }
    
    private DatabaseConnection() {}

    /**
    * Handles and opens the database connection
    *
    * @return Connection connection to database
    */
    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + DATABASE_HOST + ":" + DATABASE_PORT + "/" + DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.print(e.getMessage());
        }
        
        return connection;
    }

    /**
    * Closes current database connection
    *
    */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
