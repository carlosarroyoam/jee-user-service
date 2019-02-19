/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.crypto;

/**
 * 
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 */
public class Authentication {

    // This should be updated every year or two.
    private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, 10);

    public static String passwordHash(String password) {
        return bcrypt.encode(password);
    }

    public static boolean verifyHash(String password, String hash) {
        return bcrypt.matches(password, hash);
    }

}
