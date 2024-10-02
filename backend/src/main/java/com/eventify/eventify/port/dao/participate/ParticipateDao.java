package com.eventify.eventify.port.dao.participate;

import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.port.dao.crud.ReadDao;

import java.util.List;

public interface ParticipateDao extends ReadDao<Participate> {

    List<Participate> listByEventId(int eventId);
}
