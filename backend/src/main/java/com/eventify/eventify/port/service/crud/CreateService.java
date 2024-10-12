package com.eventify.eventify.port.service.crud;

public interface CreateService<T> {
    int create(final T entity);
}
