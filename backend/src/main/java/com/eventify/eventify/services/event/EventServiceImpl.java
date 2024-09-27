package com.eventify.eventify.services.event;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.Event;
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
    public List<Event> listPaginatedFromUser(int limit, int offset) {
        Account account = accountService.getAccountRequest();
        return eventDao.listPaginatedFromUser(limit, offset, account.getId());
    }
}
