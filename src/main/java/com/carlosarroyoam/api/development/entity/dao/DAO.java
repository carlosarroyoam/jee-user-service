/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlosarroyoam.api.development.entity.dao;

import java.util.List;

/**
 * Specifies all methods that should be implemented on dao entities classes
 *
 * @param <T> Object type
 * @author Carlos Alberto Arroyo Martínez <carlosarroyoam@gmail.com>
 */
public interface DAO<T> {

    List<T> getAll();

    T get(int id);

    T create(T t);

    boolean update(T t);

    boolean delete(T t);
}
