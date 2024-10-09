package com.eventify.eventify.port.service.crud;

public interface CrudService<T> extends
        CreateService<T>,
        ReadService<T>,
        UpdateService<T>,
        DeleteService{

}
