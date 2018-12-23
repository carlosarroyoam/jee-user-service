/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.dao;

import database.DatabaseConnection;
import database.DatabaseSchema;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class UserDAO {

    private DatabaseConnection connection;
    private static UserDAO UserDAOInstance;

    public static UserDAO getUserDAOInstance() {
        if (UserDAOInstance == null) {
            UserDAOInstance = new UserDAO();
        }
        
        return UserDAOInstance;
    }

    public UserDAO() {
        this.connection = new DatabaseConnection();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> usersArrayList = new ArrayList<>();

        try {
            String query = "SELECT id_user, name, lastname, email, password, DATE_FORMAT(createdate, \"%d/%m/%Y %h:%i %p\") as createdate FROM users";
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setIdUser(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setName(resultSet.getString(DatabaseSchema.UsersTable.Cols.NAME));
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

    public User getUserById(int id_user) {
        User user = new User();

        try {
            String query = "SELECT id_user, name, lastname, email, password, DATE_FORMAT(createdate, \"%d/%m/%Y %h:%i %p\") as createdate FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(id_user));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setIdUser(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setName(resultSet.getString(DatabaseSchema.UsersTable.Cols.NAME));
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

    public User getUserByEmail(String email) {
        User user = new User();
        try {
            String query = "SELECT id_user, name, lastname, email, password, DATE_FORMAT(createdate, \"%d/%m/%Y %h:%i %p\") as createdate FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user.setIdUser(resultSet.getInt(DatabaseSchema.UsersTable.Cols.UUID));
                user.setName(resultSet.getString(DatabaseSchema.UsersTable.Cols.NAME));
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

    public boolean insertUser(User user) {
        try {
            String query = "INSERT INTO users (name, lastname, email, password) VALUES (?,?,?,?)";
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

    public boolean updateUserById(User user) {
        try {
            String query = "UPDATE users SET name = ?, lastname = ?, email = ?, password = ? WHERE id_user= ?";
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

    public boolean deleteUserById(int id_user) {
        try {
            String query = "DELETE FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.openConnection().prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(id_user));

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
