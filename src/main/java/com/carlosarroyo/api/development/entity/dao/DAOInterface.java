/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyo.api.development.entity.dao;

import java.util.ArrayList;

/**
 *
 * @author Carlos Alberto Arroyo Martinez – carlosarroyoam@gmail.com
 * @param <T>
 */
public interface DAOInterface<T> {
    ArrayList<T> getAll();
    T get(int id);
    T create(T t);
    boolean update(T t);
    boolean delete(T t);
}
