package com.eventify.eventify.port.service.event.participate;

import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;
import com.eventify.eventify.port.service.crud.CrudService;

import java.util.List;

public interface ParticipateService extends CrudService<Participate> {
    List<ParticipateHeader> listByEventId(int eventId);

    List<Participate> listPaginatedFromUserAndNotAceptedAndNotIsOwner(final int limit, final int offset);


}
