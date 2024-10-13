package com.eventify.eventify.services.event;

import com.eventify.eventify.dto.event.EventCreateResponse;
import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.*;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.dao.event.EventDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventDao eventDao;
    private final AccountService accountService;

    public EventServiceImpl(EventDao eventDao, AccountService accountService) {
        this.eventDao = eventDao;
        this.accountService = accountService;
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
    public int createEvent(EventCreateResponse eventCreateResponse) {

        Event event = new Event(
                eventCreateResponse.title(),
                eventCreateResponse.information(),
                eventCreateResponse.createdAt(),
                eventCreateResponse.localName(),
                eventCreateResponse.cepAddress(),
                eventCreateResponse.stateAddress(),
                eventCreateResponse.cityAddress(),
                eventCreateResponse.neighborhoodAddress(),
                eventCreateResponse.numberAddress(),
                eventCreateResponse.streetAddress(),
                eventCreateResponse.complementAddress(),
                eventCreateResponse.latitude(),
                eventCreateResponse.longitude(),
                eventCreateResponse.dateStart(),
                eventCreateResponse.dateEnd(),
                eventCreateResponse.stage(),
                eventCreateResponse.pixKey()
        );

        if (event == null) {
            throw new RuntimeException();
        }

        int id = eventDao.save(event);
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
