package com.eventify.eventify.port.service.event.participate;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;
import com.eventify.eventify.port.service.crud.CrudService;

import java.util.List;

public interface ParticipateService extends CrudService<Participate> {
    List<ParticipateHeader> listByEventId(int eventId, int limit, int offset);

    List<Participate> listPaginatedFromUserAndNotAceptedAndNotIsOwner(final int limit, final int offset);

    List<Participate> readAllParticipations(int eventId);

    void deleteByUserEvents(final int id, EventListResponse response);

    int inviteMember(final int eventId, String email);

    List<Participate> findAlByEventId(int eventId);
}
