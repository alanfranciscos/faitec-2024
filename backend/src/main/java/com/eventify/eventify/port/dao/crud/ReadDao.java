package com.eventify.eventify.port.dao.crud;

public interface ReadDao<T> {

    T readById(final int id);
//
//    List<T> readAll();
}
