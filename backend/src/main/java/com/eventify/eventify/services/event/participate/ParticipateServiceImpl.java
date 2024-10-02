package com.eventify.eventify.services.event.participate;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;
import com.eventify.eventify.port.dao.participate.ParticipateDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipateServiceImpl implements ParticipateService {
    private final ParticipateDao participateDao;
    private final AccountService accountService;

    public ParticipateServiceImpl(ParticipateDao participateDao, AccountService accountService) {
        this.participateDao = participateDao;
        this.accountService = accountService;
    }


    @Override
    public List<ParticipateHeader> listByEventId(int eventId) {
        List<ParticipateHeader> participateHeaders = new ArrayList<>();

        List<Participate> participateList = participateDao.listByEventId(eventId);

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
}
