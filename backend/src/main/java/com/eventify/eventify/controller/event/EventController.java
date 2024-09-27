package com.eventify.eventify.controller.event;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.port.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<EventListResponse> listPaginatedFromUser(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        List<Event> events = eventService
                .listPaginatedFromUser(
                        limit,
                        offset
                );

        return ResponseEntity.ok(new EventListResponse(events));
    }

}
