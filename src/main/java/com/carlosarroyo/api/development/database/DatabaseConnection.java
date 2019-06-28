/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Carlos Alberto Arroyo Martinez <carlosarroyoam@gmail.com>
 */
public class DatabaseConnection {

    private static final String DATABASEHOST = "localhost";
    private static final String DATABASEPORT = "3306";
    private static final String DATABASENAME = DatabaseSchema.DATABASENAME;
    private static final String DATABASEUSER = "root";
    private static final String DATABASEPASSWORD = "toor";
    private Connection connection = null;
    
    private static DatabaseConnection DatabaseConeConnectionInstance;
    
    public static DatabaseConnection getInstance() {
        if (DatabaseConeConnectionInstance == null) {
            DatabaseConeConnectionInstance = new DatabaseConnection();
        }

        return DatabaseConeConnectionInstance;
    }
    
    private DatabaseConnection() {}

    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://" + DATABASEHOST + ":" + DATABASEPORT + "/" + DATABASENAME, DATABASEUSER, DATABASEPASSWORD);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.print(e.getMessage());
        }
        
        return connection;
    }

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
