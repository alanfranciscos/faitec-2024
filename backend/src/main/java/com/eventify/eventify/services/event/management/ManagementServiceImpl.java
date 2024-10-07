package com.eventify.eventify.services.event.management;

import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.port.dao.event.management.ManagementDao;
import com.eventify.eventify.port.service.event.management.ManagementService;

import java.util.List;

public class ManagementServiceImpl implements ManagementService {

    private final ManagementDao managementDao;

    public ManagementServiceImpl(ManagementDao managementDao) {
        this.managementDao = managementDao;
    }

    @Override
    public int create(Management entity) {
        if(entity == null){
            return 0;
        }
        int id = managementDao.save(entity);
        return id;
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            return;
        }
        managementDao.deleteById(id);
    }

    @Override
    public Management findById(int id) {
        if(id < 0){
            return null;
        }
        Management management = managementDao.readById(id);
        return management;
    }

    @Override
    public List<Management> findAll() {
        List<Management> managements = managementDao.readAll();
        return managements;
    }

    @Override
    public void update(int id, Management entity) {
        Management management = managementDao.readById(id);
        if (management == null) {
            return;
        }
        managementDao.updateInformation(id, entity);
    }

}
