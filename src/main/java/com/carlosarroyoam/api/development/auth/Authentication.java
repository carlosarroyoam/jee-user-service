/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.auth;

import com.carlosarroyoam.api.development.auth.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author Carlos Alberto Arroyo Martínez – carlosarroyoam@gmail.com
 */
public class Authentication {

    // This should be updated every year or two.
    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, 10);

    public static String passwordHash(String password) {
        return B_CRYPT_PASSWORD_ENCODER.encode(password);
    }

    public static boolean verifyHash(String password, String hash) {
        return B_CRYPT_PASSWORD_ENCODER.matches(password, hash);
    }
}
