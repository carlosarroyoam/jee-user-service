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
import com.carlosarroyoam.api.exceptions.WrongPasswordException;
import com.carlosarroyoam.api.models.User;
import java.util.Optional;

/**
 *
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class AuthService {

    private final UserService userService;
    private static AuthService authService;

    /**
     * Gets this class instance, avoids to have multiple AuthService class
     * instances.
     *
     * @return AuthService instance.
     */
    public static AuthService getInstance() {
        if (authService == null) {
            authService = new AuthService();
        }

        return authService;
    }

    /**
     * Class constructor, gets UserService instance. Needs to be private in order to
     * avoid being instanciated from external classes.
     *
     */
    private AuthService() {
        userService = UserService.getInstance();
    }

    public Optional<User> auth(User user) {
        Optional<User> toAuthUser = userService.findByEmail(user.getEmail());

        if (!Passwords.verifyHash(user.getPassword(), toAuthUser.get().getPassword())) {
            throw new WrongPasswordException("Password is not correct for " + User.class.getSimpleName() + " with email = '" + user.getEmail() + "'.");
        }

        return toAuthUser;
    }

}
