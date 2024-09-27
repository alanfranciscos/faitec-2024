package com.eventify.eventify.port.dao.crud;

public interface UpdateDao<T> {

    void updateInformation(final int id, final T entity);
}
