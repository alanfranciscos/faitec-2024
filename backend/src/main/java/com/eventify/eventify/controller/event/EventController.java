package com.eventify.eventify.controller.event;

import com.eventify.eventify.dto.event.*;
import com.eventify.eventify.dto.event.participate.ListParticipantsResponse;
import com.eventify.eventify.models.event.*;
import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;
import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import com.eventify.eventify.port.service.event.EventService;
import com.eventify.eventify.port.service.event.management.ManagementService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final ParticipateService participateService;
    private final ManagementService managementService;

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
            @PathVariable int id,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        List<EventExpanses> response = eventService
                .getExpansesById(id, limit, offset);
        int totalExpenses = eventService.getTotalExpensesForPagination(id);

        return ResponseEntity.ok(new EventExpansesResponse(
                response.stream().map(eventExpanses -> new EventExpansesResponse.Expanse(
                        eventExpanses.getId(),
                        eventExpanses.getCreatedAt(),
                        eventExpanses.getAbout(),
                        eventExpanses.getCost()
                )).toList(), totalExpenses
        ));
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<ListParticipantsResponse> getParticipants(
            @PathVariable int id,
            @RequestParam(defaultValue = "6") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        List<ParticipateHeader> response = participateService
                .listByEventId(id, limit, offset);
        int totalParticipants = eventService.getTotalParticipantsForPagination(id);

        return ResponseEntity.ok(new ListParticipantsResponse(
                response.stream().map(participateHeader -> new ListParticipantsResponse.Participant(
                        participateHeader.getId(),
                        participateHeader.getName(),
                        participateHeader.getSendedAt(),
                        participateHeader.getRoleParticipate().toString()
                )).toList(), totalParticipants
        ));
    }

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestParam(value = "image", required = false) MultipartFile imageData,
                                             @RequestParam("eventname") String eventname,
                                             @RequestParam("eventdescription") String eventdescription,
                                             @RequestParam(value = "local_name", required = false) String local_name,
                                             @RequestParam(value = "cep_address", required = false) String cep_address,
                                             @RequestParam(value = "state_address", required = false) String state_address,
                                             @RequestParam(value = "city_address", required = false) String city_address,
                                             @RequestParam(value = "neighborhood_address", required = false) String neighborhood_address,
                                             @RequestParam(value = "number_address", required = false) String number_address,
                                             @RequestParam(value = "street_address", required = false) String street_address,
                                             @RequestParam(value = "complement_address", required = false) String complement_address,
                                             @RequestParam(value = "lat", required = false) String lat,
                                             @RequestParam(value = "lng", required = false) String lng,
                                             @RequestParam("date_start") String date_start,
                                             @RequestParam("date_end") String date_end,
                                             @RequestParam(value = "pix_key", required = false) String pix_key
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDateStart = LocalDate.parse(date_start, formatter);
        LocalDate localDateEnd = LocalDate.parse(date_end, formatter);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTimeStart = localDateStart.atStartOfDay(zoneId);
        ZonedDateTime zonedDateTimeEnd = localDateEnd.atStartOfDay(zoneId);


        int eventId = this.eventService.createEvent(
                eventname,
                eventdescription,
                zonedDateTimeStart,
                zonedDateTimeEnd,
                imageData,
                local_name,
                cep_address,
                state_address,
                city_address,
                neighborhood_address,
                number_address,
                street_address,
                complement_address,
                lat,
                lng,
                pix_key
        );

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(eventId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@RequestParam(value = "event_id") int event_id,
                                            @RequestParam(value = "eventname", required = false) String eventname,
                                            @RequestParam(value = "eventdescription", required = false) String eventdescription,
                                            @RequestParam(value = "date_start", required = false) String date_start,
                                            @RequestParam(value = "date_end", required = false) String date_end,
                                            @RequestParam(value = "image", required = false) MultipartFile imageData,
                                            @RequestParam(value = "local_name", required = false) String local_name,
                                            @RequestParam(value = "cep_address", required = false) String cep_address,
                                            @RequestParam(value = "state_address", required = false) String state_address,
                                            @RequestParam(value = "city_address", required = false) String city_address,
                                            @RequestParam(value = "neighborhood_address", required = false) String neighborhood_address,
                                            @RequestParam(value = "street_address", required = false) String street_address,
                                            @RequestParam(value = "number_address", required = false) String number_address,
                                            @RequestParam(value = "lat", required = false) String lat,
                                            @RequestParam(value = "lng", required = false) String lng,
                                            @RequestParam(value = "complement_address", required = false) String complement_address,
                                            @RequestParam(value = "pix_key", required = false) String pix_key) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateStart = LocalDate.parse(date_start, formatter);
        LocalDate localDateEnd = LocalDate.parse(date_end, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTimeStart = localDateStart.atStartOfDay(zoneId);
        ZonedDateTime zonedDateTimeEnd = localDateEnd.atStartOfDay(zoneId);

        this.eventService.updateEvent(event_id,
                eventname,
                eventdescription,
                zonedDateTimeStart,
                zonedDateTimeEnd,
                imageData,
                local_name,
                cep_address,
                state_address,
                city_address,
                neighborhood_address,
                number_address,
                street_address,
                complement_address,
                lat,
                lng,
                pix_key
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable final int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok().body(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable final int id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("image/{id}")
    public ResponseEntity<String> getImageEventById(@PathVariable final int id) {
        String imageEvent = eventService.findEventImageById(id);
        return ResponseEntity.ok().body(imageEvent);
    }
}
