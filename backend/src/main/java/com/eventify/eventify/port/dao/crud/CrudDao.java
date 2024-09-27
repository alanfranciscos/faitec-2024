package com.eventify.eventify.port.dao.crud;

public interface CrudDao<T> extends
        CreateDao<T>,
        ReadDao<T>,
        UpdateDao<T>,
        DeleteDao {

}
