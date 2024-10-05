package com.eventify.eventify.port.service.crud;

//CrudService tem <T> e esse mesmo <T> automaticamente será usado no que está sendo extendido
public interface CrudService<T> extends
        CreateService<T>,
        ReadService<T>,
        UpdateService<T>,
        DeleteService{

}
