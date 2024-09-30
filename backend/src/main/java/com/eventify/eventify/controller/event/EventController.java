package com.eventify.eventify.controller.event;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.dto.event.EventOrganizationResponse;
import com.eventify.eventify.models.event.EventOrganization;
import com.eventify.eventify.port.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<EventListResponse> listPaginatedFromUser(
            @RequestParam(defaultValue = "6") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        EventListResponse response = eventService
                .listPaginatedFromUser(
                        limit,
                        offset
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/organization")
    public ResponseEntity<EventOrganizationResponse> getOrganizationById(
            @PathVariable int id
    ) {
        EventOrganization response = eventService
                .getOrganizationById(id);

        return ResponseEntity.ok(new EventOrganizationResponse(
                response.getId(),
                response.getCreatedOn(),
                response.getCreatedBy(),
                response.getNumberOfParticipants(),
                response.getStatus().toString()
        ));
    }

}
