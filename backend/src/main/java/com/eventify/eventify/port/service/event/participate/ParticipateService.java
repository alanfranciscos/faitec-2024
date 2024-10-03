package com.eventify.eventify.port.service.event.participate;

import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;

import java.util.List;

public interface ParticipateService {
    List<ParticipateHeader> listByEventId(int eventId);

    List<Participate> listPaginatedFromUserAndNotAceptedAndNotIsOwner(final int limit, final int offset);


}
