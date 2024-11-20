package com.eventify.eventify.services.event;

import com.eventify.eventify.dto.event.EventCreateResponse;
import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.dto.event.EventPaymentResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.*;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import com.eventify.eventify.port.dao.event.EventDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.EventService;
import com.eventify.eventify.port.service.event.management.ManagementService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import com.eventify.eventify.port.service.gcp.GcpStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final AccountService accountService;
    private final GcpStorageService gcpStorageService;
    private final ParticipateService participateService;
    private final ManagementService managementService;

    public EventServiceImpl(EventDao eventDao, AccountService accountService, GcpStorageService gcpStorageService, ParticipateService participateService, ManagementService managementService) {
        this.eventDao = eventDao;
        this.accountService = accountService;
        this.gcpStorageService = gcpStorageService;
        this.participateService = participateService;
        this.managementService = managementService;
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
    public List<EventExpanses> getExpansesById(int id, int limit, int offset) {
        Account account = accountService.getAccountRequest();

        boolean hasAccess = eventDao.hasAccessToEvent(id, account.getId());
        if (!hasAccess) {
            throw new IllegalArgumentException("User does not have access to event");
        }

        List<EventExpanses> response = eventDao.getExpansesById(id, limit, offset);
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
        if (eventName == null || eventDescription == null || date_start == null || date_end == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        Event event = new Event(eventName, eventDescription, date_start, date_end);

        int id = eventDao.partiallySave(event);
        this.eventDao.insertNullImage(id);
        return id;
    }

    @Override
    public int createEvent(String eventName,
                           String eventDescription,
                           ZonedDateTime date_start,
                           ZonedDateTime date_end,
                           MultipartFile imageData,
                           String local_name,
                           String cep_address,
                           String state_address,
                           String city_address,
                           String neighborhood_address,
                           String number_address,
                           String street_address,
                           String complement_address,
                           String lat,
                           String lng,
                           String pix_key
    ) {
        int eventId = this.partiallySave(eventName, eventDescription, date_start, date_end);

        if (imageData != null) {
            try {
                this.updateImage(eventId, imageData);
            } catch (Exception e) {
                this.deleteEvent(eventId);
                throw new RuntimeException("Failed to create event", e);
            }
        }

        if (local_name != null || cep_address != null || state_address != null ||
                city_address != null || neighborhood_address != null ||
                number_address != null || street_address != null || complement_address != null) {
            try {
                this.updateAddress(
                        eventId, local_name, cep_address,
                        state_address, city_address,
                        neighborhood_address, number_address,
                        street_address, complement_address,
                        lat, lng
                );
            } catch (Exception e) {
                this.deleteEvent(eventId);
                throw new RuntimeException("Failed to create event", e);
            }
        }

        if (pix_key != null) {
            try {
                this.updatePayment(eventId, pix_key);
            } catch (Exception e) {
                this.deleteEvent(eventId);
                throw new RuntimeException("Failed to create event", e);
            }
        }

        Participate participate = new Participate(eventId, RoleParticipateEnum.ORGANIZER, true);
        int participateId = this.participateService.create(participate);
        Management management = new Management(participateId, "create");
        managementService.create(management);

        return eventId;
    }

    //TODO -> Refactor this method
    @Override
    public void updateEvent(int id,
                            String eventName,
                            String eventDescription,
                            ZonedDateTime date_start,
                            ZonedDateTime date_end,
                            MultipartFile imageData,
                            String local_name,
                            String cep_address,
                            String state_address,
                            String city_address,
                            String neighborhood_address,
                            String number_address,
                            String street_address,
                            String complement_address,
                            String lat,
                            String lng,
                            String pix_key) {

        if(eventName == null || eventDescription == null || date_start == null || date_end == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        Event event = this.getEventById(id);
        if (event == null) {
            return;
        }

        if(eventName != null) {
            event.setTitle(eventName);
        }
        if(eventDescription != null) {
            event.setInformation(eventDescription);
        }
        if(date_start != null) {
            event.setDateStart(date_start);
        }
        if(date_end != null) {
            event.setDateEnd(date_end);
        }
        if(local_name != null) {
            event.setLocalName(local_name);
        }
        if(cep_address != null) {
            event.setCepAddress(cep_address);
        }
        if(state_address != null) {
            event.setStateAddress(state_address);
        }
        if(city_address != null) {
            event.setCityAddress(city_address);
        }
        if(neighborhood_address != null) {
            event.setNeighborhoodAddress(neighborhood_address);
        }
        if(number_address != null) {
            event.setNumberAddress(number_address);
        }
        if(street_address != null) {
            event.setStreetAddress(street_address);
        }
        if(complement_address != null) {
            event.setComplementAddress(complement_address);
        }
        if(lat != null) {
            event.setLatitude(Double.parseDouble(lat));
        }
        if(lng != null) {
            event.setLongitude(Double.parseDouble(lng));
        }
        if(pix_key != null) {
            event.setPixKey(pix_key);
        }

        if (imageData != null) {
            try {
                this.updateImage(id, imageData);
            } catch (Exception e) {
                this.deleteEvent(id);
                throw new RuntimeException("Failed to edit image event", e);
            }
        }
        eventDao.updateInformation(id, event);
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
        if (id < 0) {
            return null;
        }
        Event event = eventDao.readById(id);
        return event;
    }

}
