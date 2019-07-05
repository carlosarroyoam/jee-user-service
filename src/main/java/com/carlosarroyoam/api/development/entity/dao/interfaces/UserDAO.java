/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.entity.dao.interfaces;

import com.carlosarroyoam.api.development.entity.User;

/**
 * 
 * @author Carlos Alberto Arroyo Martinez <carlosarroyoam@gmail.com>
 */
public interface UserDAO extends DAOInterface<User>{
    User get(String email);
    User get(int id, String email);
}
