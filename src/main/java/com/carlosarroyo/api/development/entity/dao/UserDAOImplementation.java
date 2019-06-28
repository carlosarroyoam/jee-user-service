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
 * @author Carlos Alberto Arroyo Martinez <carlosarroyoam@gmail.com>
 */
public class UserDAOImplementation implements UserDAO {

    private DatabaseConnection connection;
    private static UserDAOImplementation UserDAOInstance;

    public static UserDAOImplementation getInstance() {
        if (UserDAOInstance == null) {
            UserDAOInstance = new UserDAOImplementation();
        }

        return UserDAOInstance;
    }

    private UserDAOImplementation() {
        this.connection = DatabaseConnection.getInstance();
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> usersArrayList;

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(DatabaseSchema.UsersTable.Cols.UUID + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.LASTNAME + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.EMAIL + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.PASSWORD + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.CREATEDAT + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.UPDATEDAT);
        query.append(" FROM " + DatabaseSchema.UsersTable.TABLENAME);

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString());
                ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.isBeforeFirst()) {
                usersArrayList = new ArrayList<>();
            } else {
                return null;
            }

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setFirstName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                user.setLastName(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                user.setRememberToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN));
                user.setCreateAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.CREATEDAT));
                user.setUpdatedAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.UPDATEDAT));

                usersArrayList.add(user);
            }

            return usersArrayList;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.closeConnection();
        }

        return null;
    }

    @Override
    public User get(int id) {
        String defaultEmail = "";
        return get(id, defaultEmail);
    }

    @Override
    public User get(String email) {
        int defaultInt = 0;
        return get(defaultInt, email);
    }

    @Override
    public User get(int id, String email) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(DatabaseSchema.UsersTable.Cols.UUID + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.LASTNAME + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.EMAIL + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.PASSWORD + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.CREATEDAT + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.UPDATEDAT);
        query.append(" FROM " + DatabaseSchema.UsersTable.TABLENAME);

        if (id > 0) {
            query.append(" WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?");
        } else if (!email.trim().equals("")) {
            query.append(" WHERE " + DatabaseSchema.UsersTable.Cols.EMAIL + " = ?");
        }

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString())) {

            if (id > 0) {
                preparedStatement.setInt(1, id);
            } else if (!email.trim().equals("")) {
                preparedStatement.setString(1, email);
            }

            User user = null;

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                    user.setFirstName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRSTNAME));
                    user.setLastName(resultSet.getString(DatabaseSchema.UsersTable.Cols.LASTNAME));
                    user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                    user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                    user.setRememberToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.REMEMBERTOKEN));
                    user.setCreateAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.CREATEDAT));
                    user.setUpdatedAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.UPDATEDAT));
                }
            }
            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.closeConnection();
        }

        return null;
    }

    @Override
    public User create(User user) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(DatabaseSchema.UsersTable.TABLENAME + " (");
        query.append(DatabaseSchema.UsersTable.Cols.FIRSTNAME + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.LASTNAME + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.EMAIL + ", ");
        query.append(DatabaseSchema.UsersTable.Cols.PASSWORD);
        query.append(") VALUES (?,?,?,?)");

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    return get(id);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.closeConnection();
        }

        return null;
    }

    @Override
    public boolean update(User user) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(DatabaseSchema.UsersTable.TABLENAME);
        query.append(" SET " + DatabaseSchema.UsersTable.Cols.FIRSTNAME + " = ?, ");
        query.append(DatabaseSchema.UsersTable.Cols.LASTNAME + " = ?, ");
        query.append(DatabaseSchema.UsersTable.Cols.EMAIL + " = ?, ");
        query.append(DatabaseSchema.UsersTable.Cols.PASSWORD + " = ? ");
        query.append(" WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?");

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString())) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getId());
            int numberOfRowsAffected = preparedStatement.executeUpdate();

            if (numberOfRowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.closeConnection();
        }

        return false;
    }

    @Override
    public boolean delete(User user) {
        StringBuilder query = new StringBuilder();

        query.append("DELETE FROM ");
        query.append(DatabaseSchema.UsersTable.TABLENAME);
        query.append(" WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?");

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString())) {
            preparedStatement.setString(1, String.valueOf(user.getId()));
            int numberOfRowsAffected = preparedStatement.executeUpdate();

            if (numberOfRowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            connection.closeConnection();
        }

        return false;
    }
}
