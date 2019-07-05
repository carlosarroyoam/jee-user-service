/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.entity.dao;

import com.carlosarroyoam.api.development.entity.dao.interfaces.UserDAO;
import com.carlosarroyoam.api.development.database.DatabaseConnection;
import com.carlosarroyoam.api.development.database.DatabaseSchema;
import com.carlosarroyoam.api.development.entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class UserDAOImplementation implements UserDAO {

    private DatabaseConnection connection;
    private static UserDAOImplementation UserDAOImplementationInstance;

    public static UserDAOImplementation getInstance() {
        if (UserDAOImplementationInstance == null) {
            UserDAOImplementationInstance = new UserDAOImplementation();
        }

        return UserDAOImplementationInstance;
    }

    private UserDAOImplementation() {
        this.connection = DatabaseConnection.getInstance();
    }

    @Override
    public List<User> getAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ").
                append(DatabaseSchema.UsersTable.Cols.UUID + ", ").
                append(DatabaseSchema.UsersTable.Cols.FIRST_NAME + ", ").
                append(DatabaseSchema.UsersTable.Cols.LAST_NAME + ", ").
                append(DatabaseSchema.UsersTable.Cols.EMAIL + ", ").
                append(DatabaseSchema.UsersTable.Cols.PASSWORD + ", ").
                append(DatabaseSchema.UsersTable.Cols.REMEMBER_TOKEN + ", ").
                append(DatabaseSchema.UsersTable.Cols.CREATED_AT + ", ").
                append(DatabaseSchema.UsersTable.Cols.UPDATED_AT).
                append(" FROM " + DatabaseSchema.UsersTable.TABLE_NAME);

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString());
                ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.isBeforeFirst()) {
                List<User> usersArrayList = new ArrayList<>();

                while (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                    user.setFirstName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRST_NAME));
                    user.setLastName(resultSet.getString(DatabaseSchema.UsersTable.Cols.LAST_NAME));
                    user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                    user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                    user.setRememberToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.REMEMBER_TOKEN));
                    user.setCreateAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.CREATED_AT));
                    user.setUpdatedAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.UPDATED_AT));

                    usersArrayList.add(user);
                }
                return usersArrayList;
            }

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
        query.append("SELECT ").
                append(DatabaseSchema.UsersTable.Cols.UUID + ", ").
                append(DatabaseSchema.UsersTable.Cols.FIRST_NAME + ", ").
                append(DatabaseSchema.UsersTable.Cols.LAST_NAME + ", ").
                append(DatabaseSchema.UsersTable.Cols.EMAIL + ", ").
                append(DatabaseSchema.UsersTable.Cols.PASSWORD + ", ").
                append(DatabaseSchema.UsersTable.Cols.REMEMBER_TOKEN + ", ").
                append(DatabaseSchema.UsersTable.Cols.CREATED_AT + ", ").
                append(DatabaseSchema.UsersTable.Cols.UPDATED_AT).
                append(" FROM " + DatabaseSchema.UsersTable.TABLE_NAME);

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

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                    user.setFirstName(resultSet.getString(DatabaseSchema.UsersTable.Cols.FIRST_NAME));
                    user.setLastName(resultSet.getString(DatabaseSchema.UsersTable.Cols.LAST_NAME));
                    user.setEmail(resultSet.getString(DatabaseSchema.UsersTable.Cols.EMAIL));
                    user.setPassword(resultSet.getString(DatabaseSchema.UsersTable.Cols.PASSWORD));
                    user.setRememberToken(resultSet.getString(DatabaseSchema.UsersTable.Cols.REMEMBER_TOKEN));
                    user.setCreateAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.CREATED_AT));
                    user.setUpdatedAt(resultSet.getTimestamp(DatabaseSchema.UsersTable.Cols.UPDATED_AT));

                    return user;
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
    public User create(User user) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").
                append(DatabaseSchema.UsersTable.TABLE_NAME + " (").
                append(DatabaseSchema.UsersTable.Cols.FIRST_NAME + ", ").
                append(DatabaseSchema.UsersTable.Cols.LAST_NAME + ", ").
                append(DatabaseSchema.UsersTable.Cols.EMAIL + ", ").
                append(DatabaseSchema.UsersTable.Cols.PASSWORD).
                append(") VALUES (?,?,?,?)");

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return get(resultSet.getInt(1));
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
        query.append("UPDATE ").
                append(DatabaseSchema.UsersTable.TABLE_NAME).
                append(" SET " + DatabaseSchema.UsersTable.Cols.FIRST_NAME + " = ?, ").
                append(DatabaseSchema.UsersTable.Cols.LAST_NAME + " = ?, ").
                append(DatabaseSchema.UsersTable.Cols.EMAIL + " = ?, ").
                append(DatabaseSchema.UsersTable.Cols.PASSWORD + " = ? ").
                append(" WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?");

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString())) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getId());

            if (preparedStatement.executeUpdate() == 1) {
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
        query.append("DELETE FROM ").
                append(DatabaseSchema.UsersTable.TABLE_NAME).
                append(" WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?");

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query.toString())) {
            preparedStatement.setString(1, String.valueOf(user.getId()));

            if (preparedStatement.executeUpdate() == 1) {
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
