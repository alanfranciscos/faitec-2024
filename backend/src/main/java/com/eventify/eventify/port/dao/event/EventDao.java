package com.eventify.eventify.port.dao.event;

import com.eventify.eventify.dto.event.EventPaymentResponse;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventDate;
import com.eventify.eventify.models.event.EventExpanses;
import com.eventify.eventify.models.event.EventOrganization;
import com.eventify.eventify.port.dao.crud.CrudDao;
import com.eventify.eventify.port.dao.crud.ReadDao;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventDao extends ListPaginatedFromUser, TotalFromUser, Expanses, CrudDao<Event> {

    boolean hasAccessToEvent(final int eventId, final int accountId);

    EventOrganization getOrganizationById(final int id);

    EventDate getDateById(final int id);

    List<EventExpanses> getExpansesById(final int id, final int limit, final int offset);

    int getTotalParticipantsForPagination(final int eventId);

    void updateImage(int id, String imagePath);

    int insertNullImage(int id);

    void updateAddress(int eventId, String local_name, String cep_address,
                       String state_address, String city_address,
                       String neighborhood_address, String number_address,
                       String street_address, String complement_address,
//                       double lat, double lng);
                       String lat, String lng);
    void updatePayment(final int eventId, final String pix_key);

    int partiallySave(Event partiallyEvent);

    void updateAcceptedAt(final int eventId);

    String getEventImageById(final int id);
}
