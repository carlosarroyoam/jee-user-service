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
import com.carlosarroyoam.api.models.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class UserService implements IUserService{

    private final UserDao userDao;
    private static UserService userServiceImpl;

    /**
    * Gets this class instance, avoids to have multiple UserServiceImpl
    * class instances.
    *
    * @return UserServiceImpl instance.
    */
    public static UserService getInstance() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserService();
        }

        return userServiceImpl;
    }

    /**
     * Class constructor, gets UserDao instance.
     * Needs to be private in order to avoid being instanciated
     * from external classes.
     * 
     */
     private UserService() {
        userDao = UserDao.getInstance();
    }
    
    @Override
    public List<User> findAll() {
        return this.userDao.getAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return this.userDao.get(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userDao.get(email);
    }
    
    @Override
    public Optional<User> save(User user) {
        user.setPassword(Passwords.toHash(user.getPassword()));
        return this.userDao.create(user);
    }

    @Override
    public Optional<User> update(User user) {
        return this.userDao.update(user);
    }

    @Override
    public boolean delete(User user) {
        return this.userDao.delete(user);
    }
    
}
