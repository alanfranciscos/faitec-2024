package com.eventify.eventify.services.event.participate;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.EventHeader;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;
import com.eventify.eventify.port.dao.participate.ParticipateDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ParticipateServiceImpl implements ParticipateService {

    private final ParticipateDao participateDao;
    private final AccountService accountService;

    public ParticipateServiceImpl(ParticipateDao participateDao, AccountService accountService) {
        this.participateDao = participateDao;
        this.accountService = accountService;
    }

    @Override
    public List<ParticipateHeader> listByEventId(int eventId, int limit, int offset) {
        List<ParticipateHeader> participateHeaders = new ArrayList<>();

        List<Participate> participateList = participateDao.listByEventId(eventId, limit, offset);

        if (participateList == null) {
            return participateHeaders;
        }

        for (Participate participate : participateList) {
            Account account = accountService.getAccountById(participate.getAccountId());
            ParticipateHeader participateHeader = new ParticipateHeader(
                    participate.getId(),
                    account.getEmail(),
                    participate.getSendedAt(),
                    participate.getRoleParticipate()
            );

            participateHeaders.add(participateHeader);
        }

        return participateHeaders;

    }

    @Override
    public List<Participate> listPaginatedFromUserAndNotAceptedAndNotIsOwner(int limit, int offset) {
        Account account = accountService.getAccountRequest();

        List<Participate> participateList = participateDao.listPaginatedFromUserAndNotAceptedAndNotIsOwner(account.getId(), limit, offset);

        if (participateList == null) {
            return new ArrayList<>();
        }

        return participateList;
    }

    @Override
    public int create(Participate entity) {
        if (entity == null) {
            return 0;
        }
        Account account = accountService.getAccountRequest();
        entity.setAccountId(account.getId());
        int id = participateDao.save(entity);
        return id;
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            return;
        }
        participateDao.deleteById(id);
    }

    @Override
    public void deleteByUserEvents(int eventId, EventListResponse response) {
        for (EventHeader event : response.events()) {
            if (event.getId() != eventId) {
                continue;
            } else {
                participateDao.deleteByEventId(eventId);
            }
        }

    }

    @Override
    public Participate findById(int id) {
        if (id < 0) {
            return null;
        }
        Participate participate = participateDao.readById(id);
        return participate;
    }

    @Override
    public List<Participate> findAll() {
        List<Participate> participates = participateDao.readAll();
        return participates;
    }

    @Override
    public void update(int id, Participate entity) {
        Participate participate = findById(id);
        if (participate == null) {
            return;
        }
        participateDao.updateInformation(id, entity);
    }

    @Override
    public List<Participate> readAllParticipations(int id) {
        Participate participate = findById(id);
        if (participate == null) {
            throw new RuntimeException("Invalid id");
        }
        List<Participate> participations = participateDao.readByAccountId(id);
        return participations;
    }
}
