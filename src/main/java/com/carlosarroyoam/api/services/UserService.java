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
package com.carlosarroyoam.api.services;

import com.carlosarroyoam.api.auth.Passwords;
import com.carlosarroyoam.api.dao.UserDao;
import com.carlosarroyoam.api.exceptions.ResourceNotCreatedException;
import com.carlosarroyoam.api.exceptions.ResourceNotDeletedException;
import com.carlosarroyoam.api.exceptions.ResourceNotFoundException;
import com.carlosarroyoam.api.exceptions.ResourceNotUpdatedException;
import com.carlosarroyoam.api.models.User;
import java.util.List;
import java.util.Optional;

/**
 * User bussines logic service class.
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class UserService {

    private final UserDao userDao;
    private static UserService userService;

    /**
     * Gets this class instance, avoids to have multiple UserServiceImpl class
     * instances.
     *
     * @return UserServiceImpl instance.
     */
    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }

        return userService;
    }

    /**
     * Class constructor, gets UserDao instance. Needs to be private in order to
     * avoid being instanciated from external classes.
     *
     */
    private UserService() {
        userDao = UserDao.getInstance();
    }

    
    public List<User> findAll() {
        return this.userDao.getAll();
    }

    public Optional<User> findById(int id) {
        Optional<User> user = this.userDao.get(id);

        if (!user.isPresent()) {
            throw new ResourceNotFoundException(String.format("%s with id = '%d' was not found", User.class.getSimpleName(), id));
        }

        return user;
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> user = this.userDao.get(email);

        if (!user.isPresent()) {
            throw new ResourceNotFoundException(String.format("%s with email = '%d' was not found", User.class.getSimpleName(), email));
        }

        return user;
    }

    public Optional<User> save(User user) {
        if (user.getId() > 0) {
            Optional<User> updatedUser = this.userDao.update(user);

            if (!updatedUser.isPresent()) {
                throw new ResourceNotUpdatedException(String.format("%s was not updated", User.class.getSimpleName()));
            }

            return updatedUser;
        }

        user.setPassword(Passwords.toHash(user.getPassword()));

        Optional<User> createdUser = this.userDao.create(user);
        if (!createdUser.isPresent()) {
            throw new ResourceNotCreatedException(String.format("%s was not created", User.class.getSimpleName()));
        }

        return createdUser;
    }

    public boolean delete(int id) {
        Optional<User> userToDelete = findById(id);

        if (!this.userDao.delete(userToDelete.get())) {
            throw new ResourceNotDeletedException(String.format("%s was not deleted", User.class.getSimpleName()));
        }

        return true;
    }

}
