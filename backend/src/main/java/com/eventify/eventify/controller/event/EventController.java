package com.eventify.eventify.controller.event;

import com.eventify.eventify.dto.event.*;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventDate;
import com.eventify.eventify.models.event.EventExpanses;
import com.eventify.eventify.models.event.EventOrganization;
import com.eventify.eventify.port.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}/total-expanses")
    public ResponseEntity<TotalExpansesResponse> totalExpenses(
            @PathVariable int id
    ) {
        double response = eventService
                .totalExpanses(id);

        return ResponseEntity.ok(new TotalExpansesResponse(
                response
        ));
    }

    @GetMapping("/{id}/dates")
    public ResponseEntity<EventDatesResponse> getDates(
            @PathVariable int id
    ) {
        EventDate response = eventService
                .getDateById(id);

        return ResponseEntity.ok(new EventDatesResponse(
                response.getStartDate(),
                response.getEndDate()
        ));
    }

    @GetMapping("/{id}/localization")
    public ResponseEntity<EventLocalizationResponse> getLocalization(
            @PathVariable int id
    ) {
        Event response = eventService
                .getEventById(id);


        return ResponseEntity.ok(new EventLocalizationResponse(
                response.getLocalName(),
                response.getCepAddress(),
                response.getStateAddress(),
                response.getCityAddress(),
                response.getNeighborhoodAddress(),
                response.getStreetAddress(),
                response.getNumberAddress(),
                response.getComplementAddress(),
                new EventLocalizationResponse.Coordinates(
                        response.getLatitude(),
                        response.getLongitude()
                )
        ));
    }

    @GetMapping("/{id}/payment")
    public ResponseEntity<EventPaymentResponse> getPayment(
            @PathVariable int id
    ) {
        Event response = eventService
                .getEventById(id);

        return ResponseEntity.ok(new EventPaymentResponse(
                response.getPixKey()
        ));
    }

    @GetMapping("/{id}/expanses")
    public ResponseEntity<EventExpansesResponse> getExpanses(
            @PathVariable int id
    ) {
        List<EventExpanses> response = eventService
                .getExpansesById(id);


        return ResponseEntity.ok(new EventExpansesResponse(
                response.stream().map(eventExpanses -> new EventExpansesResponse.Expanse(
                        eventExpanses.getCreatedAt(),
                        eventExpanses.getAbout(),
                        eventExpanses.getCost()

                )).toList()
        ));
    }

}
