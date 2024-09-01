package com.eventify.eventify.config.env;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Dotenv dotenv = Dotenv.load();

        Map<String, Object> envVariables = new HashMap<>();
        dotenv.entries().forEach(entry -> envVariables.put(entry.getKey(), entry.getValue()));

        ConfigurableEnvironment environment = event.getEnvironment();
        environment.getPropertySources().addFirst(new MapPropertySource("dotenv", envVariables));

    }

}
