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
import java.sql.Statement;
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
            String query = "SELECT "
                    + DatabaseSchema.UsersTable.Cols.UUID + ", "
                    + DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", "
                    + DatabaseSchema.UsersTable.Cols.LASTNAME + ", "
                    + DatabaseSchema.UsersTable.Cols.EMAIL + ", "
                    + DatabaseSchema.UsersTable.Cols.PASSWORD + ", "
                    + DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN + ", "
                    + DatabaseSchema.UsersTable.Cols.APITOKEN + ", "
                    + DatabaseSchema.UsersTable.Cols.CREATEDAT + ", "
                    + DatabaseSchema.UsersTable.Cols.UPDATEDAT
                    + " FROM " + DatabaseSchema.UsersTable.TABLENAME;

            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setFirstName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                user.setLastName(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                user.setRememberToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN));
                user.setApiToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.APITOKEN));
                user.setCreateAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.CREATEDAT));
                user.setUpdatedAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.UPDATEDAT));

                usersArrayList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            connection.closeConnection();
            return usersArrayList;
        }
    }

    public User get(int id) {
        String defaultEmail = "";
        return get(id, defaultEmail);
    }

    public User get(String email) {
        int defaultInt = 0;
        return get(defaultInt, email);
    }

    public User get(int id, String email) {
        User user = new User();

        try {
            String query = "SELECT "
                    + DatabaseSchema.UsersTable.Cols.UUID + ", "
                    + DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", "
                    + DatabaseSchema.UsersTable.Cols.LASTNAME + ", "
                    + DatabaseSchema.UsersTable.Cols.EMAIL + ", "
                    + DatabaseSchema.UsersTable.Cols.PASSWORD + ", "
                    + DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN + ", "
                    + DatabaseSchema.UsersTable.Cols.APITOKEN + ", "
                    + DatabaseSchema.UsersTable.Cols.CREATEDAT + ", "
                    + DatabaseSchema.UsersTable.Cols.UPDATEDAT
                    + " FROM " + DatabaseSchema.UsersTable.TABLENAME
                    + " WHERE ";

            if (id > 0) {
                query += DatabaseSchema.UsersTable.Cols.UUID + " = ?";
            } else if (!email.trim().equals("")) {
                query += DatabaseSchema.UsersTable.Cols.EMAIL + " = ?";
            }

            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);

            if (id > 0) {
                preparedStatement.setString(1, String.valueOf(id));
            } else if (!email.trim().equals("")) {
                preparedStatement.setString(1, email);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setFirstName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                user.setLastName(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                user.setRememberToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN));
                user.setApiToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.APITOKEN));
                user.setCreateAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.CREATEDAT));
                user.setUpdatedAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.UPDATEDAT));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            connection.closeConnection();
            return user;
        }
    }

    public User create(User user) {
        try {
            String query = "INSERT INTO "
                    + DatabaseSchema.UsersTable.TABLENAME + " ("
                    + DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", "
                    + DatabaseSchema.UsersTable.Cols.LASTNAME + ", "
                    + DatabaseSchema.UsersTable.Cols.EMAIL + ", "
                    + DatabaseSchema.UsersTable.Cols.PASSWORD + ", "
                    + DatabaseSchema.UsersTable.Cols.APITOKEN
                    + ") VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getApiToken());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                return get(id);
            }

            return new User();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new User();
        } finally {
            connection.closeConnection();
        }
    }

    public boolean update(User user) {
        try {
            String query = "UPDATE "
                    + DatabaseSchema.UsersTable.TABLENAME
                    + " SET " + DatabaseSchema.UsersTable.Cols.FIRSTNAME + " = ?, "
                    + DatabaseSchema.UsersTable.Cols.LASTNAME + " = ?, "
                    + DatabaseSchema.UsersTable.Cols.EMAIL + " = ?, "
                    + DatabaseSchema.UsersTable.Cols.PASSWORD + " = ? "
                    + DatabaseSchema.UsersTable.Cols.APITOKEN + " = ? "
                    + " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";

            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getApiToken());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            connection.closeConnection();
        }
    }

    public boolean delete(int id) {
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
