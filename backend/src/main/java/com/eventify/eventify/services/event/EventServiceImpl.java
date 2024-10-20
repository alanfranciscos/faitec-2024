package com.eventify.eventify.services.event;

import com.eventify.eventify.dto.event.EventCreateResponse;
import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.dto.event.EventPaymentResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.*;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.dao.event.EventDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.EventService;
import com.eventify.eventify.port.service.gcp.GcpStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final AccountService accountService;
    private final GcpStorageService gcpStorageService;

    public EventServiceImpl(EventDao eventDao, AccountService accountService, GcpStorageService gcpStorageService) {
        this.eventDao = eventDao;
        this.accountService = accountService;
        this.gcpStorageService = gcpStorageService;
    }

    @Override
    public EventListResponse listPaginatedFromUser(int limit, int offset) {
        Account account = accountService.getAccountRequest();

        List<EventHeader> events = eventDao.listPaginatedHeaderFromUser(limit, offset, account.getId());
        int total = eventDao.totalFromUser(account.getId());

        EventListResponse response = new EventListResponse(
                events,
                total
        );

        return response;
    }

    @Override
    public EventOrganization getOrganizationById(int id) {
        Account account = accountService.getAccountRequest();

        boolean hasAccess = eventDao.hasAccessToEvent(id, account.getId());
        if (!hasAccess) {
            throw new IllegalArgumentException("User does not have access to event");
        }

        EventOrganization response = eventDao.getOrganizationById(id);
        return response;
    }

    @Override
    public double totalExpanses(int eventId) {
        Account account = accountService.getAccountRequest();

        boolean hasAccess = eventDao.hasAccessToEvent(eventId, account.getId());
        if (!hasAccess) {
            throw new IllegalArgumentException("User does not have access to event");
        }

        double total = eventDao.totalExpenses(eventId);
        return total;
    }

    @Override
    public List<EventExpanses> getExpansesById(int id) {
        Account account = accountService.getAccountRequest();

        boolean hasAccess = eventDao.hasAccessToEvent(id, account.getId());
        if (!hasAccess) {
            throw new IllegalArgumentException("User does not have access to event");
        }

        List<EventExpanses> response = eventDao.getExpansesById(id);
        return response;
    }

    @Override
    public int getTotalExpensesForPagination(int eventId) {
        int totalExpenses = eventDao.getTotalExpensesForPagination(eventId);
        return totalExpenses;
    }

    @Override
    public int getTotalParticipantsForPagination(int eventId) {
        int totalParticipants = eventDao.getTotalParticipantsForPagination(eventId);
        return totalParticipants;
    }

    @Override
    public void updateImage(int eventId, MultipartFile imageData) {
        String imageUrl = "";

        try {
            String fileName = "1.png";
            String bucketPath = "events/" + eventId + "/";
            imageUrl = gcpStorageService.uploadImage(imageData, fileName, bucketPath);
        } catch (IOException e) {
            throw new RuntimeException("Bucket error: ", e);
        }

        try {
            eventDao.updateImage(eventId, imageUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update image in database: ", e);
        }
    }

    @Override
    public void updateAddress(
            int eventId, String local_name, String cep_address,
            String state_address, String city_address, String neighborhood_address,
            String number_address, String street_address, String complement_address,
//            double lat, double lng
            String lat, String lng
    ) {
        try {
            eventDao.updateAddress(eventId, local_name, cep_address,
                    state_address, city_address,
                    neighborhood_address, number_address,
//                    street_address, complement_address, lat, lng);
                    street_address, complement_address, lat, lng);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update address in database: ", e);
        }
    }

    @Override
    public void updatePayment(int eventId, String pixKeY) {
        try {
            eventDao.updatePayment(eventId, pixKeY);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update payment in database: ", e);
        }
    }

    @Override
    public void updateAceptedAt(int eventId) {
        try {
            eventDao.updateAcceptedAt(eventId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update accepted at from event in database: ", e);
        }
    }

    @Override
    public String findEventImageById(int id) {
        return eventDao.getEventImageById(id);
    }


    @Override
    public EventDate getDateById(int id) {
        Account account = accountService.getAccountRequest();

        boolean hasAccess = eventDao.hasAccessToEvent(id, account.getId());
        if (!hasAccess) {
            throw new IllegalArgumentException("User does not have access to event");
        }

        EventDate response = eventDao.getDateById(id);
        return response;
    }

    @Override
    public Event getEventById(int id) {
        Account account = accountService.getAccountRequest();

        boolean hasAccess = eventDao.hasAccessToEvent(id, account.getId());
        if (!hasAccess) {
            throw new IllegalArgumentException("User does not have access to event");
        }

        Event response = eventDao.readById(id);
        return response;
    }

    @Override
    public int partiallySave(String eventName, String eventDescription, ZonedDateTime date_start, ZonedDateTime date_end) {
        Event event = new Event(eventName, eventDescription, date_start, date_end);

        if (event == null) {
            throw new RuntimeException();
        }

        int id = eventDao.partiallySave(event);
        this.eventDao.insertNullImage(id);
        return id;
    }

    @Override
    public void updateEvent(int id, Event entity) {
        Event event = getEventById(id);
        if (event == null) {
            return;
        }
        eventDao.updateInformation(id, entity);
    }

    @Override
    public void deleteEvent(int id) {
        if (id < 0) {
            return;
        }

        eventDao.deleteById(id);
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = eventDao.readAll();
        return events;
    }

    @Override
    public Event findById(int id) {
        if(id < 0){
            return null;
        }
        Event event = eventDao.readById(id);
        return event;
    }

}
