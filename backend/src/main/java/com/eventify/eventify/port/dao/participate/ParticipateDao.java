package com.eventify.eventify.port.dao.participate;

import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.port.dao.crud.CrudDao;
import com.eventify.eventify.port.dao.crud.ReadDao;

import java.util.List;

public interface ParticipateDao extends ReadDao<Participate>, CrudDao<Participate> {

    List<Participate> listByEventId(int eventId, int limit, int offset);

    List<Participate> listPaginatedFromUserAndNotAceptedAndNotIsOwner(int accountId, int limit, int offset);

    List<Participate> readByAccountId(int id);
}
