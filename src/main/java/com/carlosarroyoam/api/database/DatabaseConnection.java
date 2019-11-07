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

package com.carlosarroyoam.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains methods for managing MySQL database connections.
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
    
    private static DatabaseConnection DatabaseConnectionInstance;
    
    /**
    * Gets this class instance, avoids to have multiple DatabaseConnection
    * class instances.
    *
    * @return DatabaseConnection instance.
    */
    public static DatabaseConnection getInstance() {
        if (DatabaseConnectionInstance == null) {
            DatabaseConnectionInstance = new DatabaseConnection();
        }

        return DatabaseConnectionInstance;
    }
    
    /**
     * Class constructor, needs to be private in order to avoid being
     * instanciated from external classes.
     * 
     */
    private DatabaseConnection() {}

    /**
    * Handles and opens the database connection.
    *
    * @return Connection connection to database.
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
    * Closes current database connection.
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
