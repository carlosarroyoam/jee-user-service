/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.entity.dao;

import com.carlosarroyoam.api.development.database.DatabaseConnection;
import com.carlosarroyoam.api.development.database.DatabaseSchema;
import com.carlosarroyoam.api.development.entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.carlosarroyoam.api.development.entity.dao.interfaces.Dao;

/**
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class UserDao implements Dao<User> {

    private final DatabaseConnection connection;
    private static UserDao UserDAOImplementationInstance;

    public static UserDao getInstance() {
        if (UserDAOImplementationInstance == null) {
            UserDAOImplementationInstance = new UserDao();
        }

        return UserDAOImplementationInstance;
    }

    private UserDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT "
                + DatabaseSchema.UsersTable.Cols.UUID + ", "
                + DatabaseSchema.UsersTable.Cols.FIRST_NAME + ", "
                + DatabaseSchema.UsersTable.Cols.LAST_NAME + ", "
                + DatabaseSchema.UsersTable.Cols.EMAIL + ", "
                + DatabaseSchema.UsersTable.Cols.PASSWORD + ", "
                + DatabaseSchema.UsersTable.Cols.REMEMBER_TOKEN + ", "
                + DatabaseSchema.UsersTable.Cols.CREATED_AT + ", "
                + DatabaseSchema.UsersTable.Cols.UPDATED_AT
                + " FROM " + DatabaseSchema.UsersTable.TABLE_NAME;

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
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

        return Collections.emptyList();
    }

    @Override
    public User get(int id) {
        String defaultEmail = "";
        return getByIdOrEmail(id, defaultEmail);
    }

    public User get(String email) {
        int defaultInt = 0;
        return getByIdOrEmail(defaultInt, email);
    }

    private User getByIdOrEmail(int id, String email) {
        String query = "SELECT "
                + DatabaseSchema.UsersTable.Cols.UUID + ", "
                + DatabaseSchema.UsersTable.Cols.FIRST_NAME + ", "
                + DatabaseSchema.UsersTable.Cols.LAST_NAME + ", "
                + DatabaseSchema.UsersTable.Cols.EMAIL + ", "
                + DatabaseSchema.UsersTable.Cols.PASSWORD + ", "
                + DatabaseSchema.UsersTable.Cols.REMEMBER_TOKEN + ", "
                + DatabaseSchema.UsersTable.Cols.CREATED_AT + ", "
                + DatabaseSchema.UsersTable.Cols.UPDATED_AT
                + " FROM " + DatabaseSchema.UsersTable.TABLE_NAME;

        if (id > 0) {
            query += " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";
        } else if (!email.trim().equals("")) {
            query += " WHERE " + DatabaseSchema.UsersTable.Cols.EMAIL + " = ?";
        }

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query)) {

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
        String query = "INSERT INTO "
                + DatabaseSchema.UsersTable.TABLE_NAME + " ("
                + DatabaseSchema.UsersTable.Cols.FIRST_NAME + ", "
                + DatabaseSchema.UsersTable.Cols.LAST_NAME + ", "
                + DatabaseSchema.UsersTable.Cols.EMAIL + ", "
                + DatabaseSchema.UsersTable.Cols.PASSWORD
                + ") VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
        String query = "UPDATE "
                + DatabaseSchema.UsersTable.TABLE_NAME
                + " SET " + DatabaseSchema.UsersTable.Cols.FIRST_NAME + " = ?, "
                + DatabaseSchema.UsersTable.Cols.LAST_NAME + " = ?, "
                + DatabaseSchema.UsersTable.Cols.EMAIL + " = ?, "
                + DatabaseSchema.UsersTable.Cols.PASSWORD + " = ? "
                + " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query)) {
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
        String query = "DELETE FROM "
                + DatabaseSchema.UsersTable.TABLE_NAME
                + " WHERE " + DatabaseSchema.UsersTable.Cols.UUID + " = ?";

        try (PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query)) {
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
