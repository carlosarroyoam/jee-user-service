/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.auth;

import com.carlosarroyoam.api.development.auth.bcrypt.BCryptPasswordEncoder;

/**
 * This class contains methods for passwords encryption, and passwords
 * verification
 * 
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public class Authentication {

    // This should be updated every year or two.
    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, 10);

    /**
     * Encrypts a plain text string using PASSWORD_BCRYPT algorithm
     * 
     * @param password plain text password
     * @return String encrypted password
     */
    public static String passwordHash(String password) {
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
