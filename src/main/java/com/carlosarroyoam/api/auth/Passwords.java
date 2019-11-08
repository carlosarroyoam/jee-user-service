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

package com.carlosarroyoam.api.auth;

import com.carlosarroyoam.api.auth.bcrypt.BCryptPasswordEncoder;

/**
 * This class contains methods for passwords encryption, and passwords
 * verification
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class Passwords {

    // This should be updated every year or two.
    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, 10);

    /**
     * Encrypts a plain text string using PASSWORD_BCRYPT algorithm
     * 
     * @param password plain text password
     * @return String encrypted password
     */
    public static String toHash(String password) {
        return B_CRYPT_PASSWORD_ENCODER.encode(password);
    }

    /**
     * Verifies a plain text string is equal to a hashed password, returns true
     * when plain text password is equal to hashed password
     * 
     * @param password plain text password to be verified
     * @param hash  hashed password string
     * @return boolean
     */
    public static boolean verifyHash(String password, String hash) {
        return B_CRYPT_PASSWORD_ENCODER.matches(password, hash);
    }
    
}
