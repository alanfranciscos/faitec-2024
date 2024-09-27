package com.eventify.eventify.port.dao.crud;

public interface CreateDao<T> {
    int save(final T entity);
}
