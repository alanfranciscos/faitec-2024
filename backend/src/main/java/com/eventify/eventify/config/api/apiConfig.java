package com.eventify.eventify.config.api;

import com.eventify.eventify.dao.account.AccountDaoImpl;
import com.eventify.eventify.dao.account.password.AccountPasswordHistoryDaoImpl;
import com.eventify.eventify.dao.event.EventDaoImpl;
import com.eventify.eventify.port.dao.account.AccountDao;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;
import com.eventify.eventify.port.dao.event.EventDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;

@Configuration
public class apiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AccountDao geAccountDao(final Connection connection) {
        return new AccountDaoImpl(connection);
    }

    @Bean
    public AccountPasswordHistoryDao geAccountPsswordHistoryDao(final Connection connection) {
        return new AccountPasswordHistoryDaoImpl(connection);
    }

    @Bean
    public EventDao getEventDao(final Connection connection) {
        return new EventDaoImpl(connection);
    }

}
