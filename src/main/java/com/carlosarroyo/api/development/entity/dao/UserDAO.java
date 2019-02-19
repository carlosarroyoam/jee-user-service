/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.dao;

import com.carlosarroyo.api.development.database.DatabaseConnection;
import com.carlosarroyo.api.development.database.DatabaseSchema;
import com.carlosarroyo.api.development.entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
public class UserDAO {

    private DatabaseConnection connection;
    private static UserDAO UserDAOInstance;

    public static UserDAO getInstance() {
        if (UserDAOInstance == null) {
            UserDAOInstance = new UserDAO();
        }

        return UserDAOInstance;
    }

    public UserDAO() {
        this.connection = new DatabaseConnection();
    }

    public ArrayList<User> getAll() {
        ArrayList<User> usersArrayList = new ArrayList<>();

        try {
            String query = "SELECT " +
                    DatabaseSchema.UsersTable.Cols.UUID + ", " +
                    DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.LASTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.EMAIL + ", " +
                    DatabaseSchema.UsersTable.Cols.PASSWORD + ", " +
                    "DATE_FORMAT(" + DatabaseSchema.UsersTable.Cols.CREATEDATE + ", \"%d/%m/%Y %h:%i %p\") as createdate " +
                    " FROM " + DatabaseSchema.UsersTable.TABLENAME;
            
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setIdUser(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                user.setLastname(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                user.setCreatedate(resultSet.getString(DatabaseSchema.UsersTable.Cols.CREATEDATE));

                usersArrayList.add(user);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            connection.closeConnection();
            return usersArrayList;

        }
    }

    public User getById(int id) {
        User user = new User();

        try {
            String query = "SELECT " +
                    DatabaseSchema.UsersTable.Cols.UUID + ", " +
                    DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.LASTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.EMAIL + ", " +
                    DatabaseSchema.UsersTable.Cols.PASSWORD + ", " +
                    "DATE_FORMAT(" + DatabaseSchema.UsersTable.Cols.CREATEDATE + ", \"%d/%m/%Y %h:%i %p\") as createdate " +
                    " FROM " + DatabaseSchema.UsersTable.TABLENAME +
                    " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";
            
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(id));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setIdUser(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                user.setLastname(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                user.setCreatedate(resultSet.getString(DatabaseSchema.UsersTable.Cols.CREATEDATE));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            connection.closeConnection();
            return user;

        }
    }

    public User getByEmail(String email) {
        User user = new User();
        try {
            String query = "SELECT " +
                    DatabaseSchema.UsersTable.Cols.UUID + ", " +
                    DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.LASTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.EMAIL + ", " +
                    DatabaseSchema.UsersTable.Cols.PASSWORD + ", " +
                    "DATE_FORMAT(" + DatabaseSchema.UsersTable.Cols.CREATEDATE + ", \"%d/%m/%Y %h:%i %p\") as createdate " +
                    " FROM " + DatabaseSchema.UsersTable.TABLENAME +
                    " WHERE " + DatabaseSchema.UsersTable.Cols.EMAIL + " = ?";
            
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setIdUser(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                user.setLastname(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                user.setCreatedate(resultSet.getString(DatabaseSchema.UsersTable.Cols.CREATEDATE));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            connection.closeConnection();
            return user;

        }
    }

    public boolean insert(User user) {
        try {
            String query = "INSERT INTO " +
                    DatabaseSchema.UsersTable.TABLENAME + " (" +
                    DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.LASTNAME + ", " +
                    DatabaseSchema.UsersTable.Cols.EMAIL + ", " +
                    DatabaseSchema.UsersTable.Cols.PASSWORD +
                    ") VALUES (?,?,?,?)";
            
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            connection.closeConnection();

        }
    }

    public boolean updateById(User user) {
        try {
            String query = "UPDATE " +
                    DatabaseSchema.UsersTable.TABLENAME +
                    " SET " + DatabaseSchema.UsersTable.Cols.FIRSTNAME + " = ?, " +
                    DatabaseSchema.UsersTable.Cols.LASTNAME + " = ?, " +
                    DatabaseSchema.UsersTable.Cols.EMAIL + " = ?, " +
                    DatabaseSchema.UsersTable.Cols.PASSWORD + " = ? " +
                    " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";

            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getIdUser());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            connection.closeConnection();

        }
    }

    public boolean deleteById(int id) {
        try {
            String query = "DELETE FROM "
                    + DatabaseSchema.UsersTable.TABLENAME
                    + " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";

            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(id));

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            connection.closeConnection();

        }
    }

}
