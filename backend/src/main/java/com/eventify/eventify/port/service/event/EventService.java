package com.eventify.eventify.port.service.event;

import com.eventify.eventify.dto.event.EventCreateResponse;
import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.dto.event.EventPaymentResponse;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventDate;
import com.eventify.eventify.models.event.EventOrganization;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventService extends ExpenseForEventService {

    EventListResponse listPaginatedFromUser(final int limit, final int offset);

    EventOrganization getOrganizationById(final int id);

    EventDate getDateById(final int id);

    Event getEventById(final int id);

    int createEvent(String eventName, String eventDescription, ZonedDateTime date_start, ZonedDateTime date_end);

    void updateEvent(final int id, final Event event);

    void deleteEvent(final int id);

    List<Event> findAll();

    Event findById(final int id);

    int getTotalParticipantsForPagination(final int eventId);

    void updateImage(final int eventId, final MultipartFile imageData);

    void updateAddress(String local_name, String cep_address,
                       String state_address, String city_address,
                       String neighborhood_address, String number_address,
                       String street_address, String complement_address);
}
