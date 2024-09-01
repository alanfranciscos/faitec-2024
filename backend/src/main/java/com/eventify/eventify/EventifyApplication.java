package com.eventify.eventify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eventify.eventify.config.env.EnvLoader;

@SpringBootApplication
public class EventifyApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EventifyApplication.class);
		application.addListeners(new EnvLoader());
		application.run(args);
	}

}
