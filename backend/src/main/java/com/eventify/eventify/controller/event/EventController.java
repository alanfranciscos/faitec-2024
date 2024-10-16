package com.eventify.eventify.controller.event;

import com.eventify.eventify.dto.event.*;
import com.eventify.eventify.dto.event.participate.ListParticipantsResponse;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventDate;
import com.eventify.eventify.models.event.EventExpanses;
import com.eventify.eventify.models.event.EventOrganization;
import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.ParticipateHeader;
import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import com.eventify.eventify.port.service.event.EventService;
import com.eventify.eventify.port.service.event.management.ManagementService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

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
            @PathVariable int id
    ) {
        List<EventExpanses> response = eventService
                .getExpansesById(id);
        int totalExpenses = eventService.getTotalExpensesForPagination(id);

        return ResponseEntity.ok(new EventExpansesResponse(
                response.stream().map(eventExpanses -> new EventExpansesResponse.Expanse(
                        eventExpanses.getCreatedAt(),
                        eventExpanses.getAbout(),
                        eventExpanses.getCost()

                )).toList(), totalExpenses
        ));
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<ListParticipantsResponse> getParticipants(
            @PathVariable int id
    ) {
        List<ParticipateHeader> response = participateService
                .listByEventId(id);
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

//    @PostMapping()
//    public ResponseEntity<Event> createEvent(@RequestParam(value = "image", required = false) MultipartFile imageData,
//                                             @RequestParam("eventname") String eventname,
//                                             @RequestParam("eventdescription") String eventdescription,
//                                             @RequestParam(value = "local_name", required = false) String local_name,
//                                             @RequestParam(value = "cep_address", required = false) String cep_address,
//                                             @RequestParam(value = "state_address", required = false) String state_address,
//                                             @RequestParam(value = "city_address", required = false) String city_address,
//                                             @RequestParam(value = "neighborhood_address", required = false) String neighborhood_address,
//                                             @RequestParam(value = "number_address", required = false) String number_address,
//                                             @RequestParam(value = "street_address", required = false) String street_address,
//                                             @RequestParam(value = "complement_address", required = false) String complement_address,
//                                             @RequestParam("date_start") ZonedDateTime date_start,
//                                             @RequestParam("date_end") ZonedDateTime date_end,
//                                             @RequestParam(value = "pix_key", required = false) String pix_key
//                                             ){
//        int eventId = this.eventService.partiallySave(
//                eventname,
//                eventdescription,
//                date_start,
//                date_end
//        );
//        if (imageData != null) {
//            try {
//                this.eventService.updateImage(eventId, imageData);
//            } catch (Exception e) {
//                this.eventService.deleteEvent(eventId);
//                throw new RuntimeException("Failed to create event", e);
//            }
//        }
//
//        if (local_name != null || cep_address != null || state_address != null ||
//                city_address != null || neighborhood_address != null ||
//                number_address != null || street_address != null || complement_address != null) {
//            try {
//                this.eventService.updateAddress(eventId, local_name, cep_address,
//                        state_address, city_address,
//                        neighborhood_address, number_address,
//                        street_address, complement_address);
//            } catch (Exception e) {
//                this.eventService.deleteEvent(eventId);
//                throw new RuntimeException("Failed to create event", e);
//            }
//        }
//
//        if (pix_key != null) {
//            try {
//                this.eventService.updatePayment(eventId, pix_key);
//            } catch (Exception e) {
//                this.eventService.deleteEvent(eventId);
//                throw new RuntimeException("Failed to create event", e);
//            }
//        }
//
//        final URI uri = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(eventId)
//                .toUri();
//        return ResponseEntity.created(uri).build();
//    }

    /**
     *
     * @param imageData
     * @param event
     *     {
     *         "eventname": "Festival de Música",
     *             "eventdescription": "Um festival incrível com várias bandas!",
     *             "local_name": "Praça Central",
     *             "cep_address": "12345-678",
     *             "state_address": "SP",
     *             "city_address": "São Paulo",
     *             "neighborhood_address": "Centro",
     *             "number_address": "100",
     *             "street_address": "Rua das Flores",
     *             "complement_address": "Próximo ao parque",
     *             "date_start": "2024-12-25T20:46:19.645594-03:00",
     *             "date_end": "2024-12-29T20:46:19.645594-03:00",
     *             "pix_key": "12345678940"
     *     }
     * @return
     */
    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestParam(value = "image", required = false) MultipartFile imageData,
                                             @RequestBody Event event){
        int eventId = this.eventService.partiallySave(
                event.getTitle(),
                event.getInformation(),
                event.getDateStart(),
                event.getDateEnd()
        );
        if (imageData != null) {
            try {
                this.eventService.updateImage(eventId, imageData);
            } catch (Exception e) {
                this.eventService.deleteEvent(eventId);
                throw new RuntimeException("Failed to create event", e);
            }
        }

        if (event.getLocalName() != null || event.getCepAddress() != null || event.getStateAddress() != null ||
                event.getCityAddress() != null || event.getNeighborhoodAddress() != null ||
                event.getNumberAddress() != null || event.getStreetAddress() != null || event.getComplementAddress() != null) {
            try {
                this.eventService.updateAddress(eventId, event.getLocalName(), event.getCepAddress(),
                        event.getStateAddress(), event.getCityAddress(),
                        event.getNeighborhoodAddress(), event.getNumberAddress(),
                        event.getStreetAddress(), event.getComplementAddress());
            } catch (Exception e) {
                this.eventService.deleteEvent(eventId);
                throw new RuntimeException("Failed to create event", e);
            }
        }

        if (event.getPixKey() != null) {
            try {
                this.eventService.updatePayment(eventId, event.getPixKey());
            } catch (Exception e) {
                this.eventService.deleteEvent(eventId);
                throw new RuntimeException("Failed to create event", e);
            }
        }
        Participate participate = new Participate(eventId, RoleParticipateEnum.ORGANIZER, true);
        int participateId = participateService.create(participate);
        Management management = new Management(participateId, "create");
        int managementId = managementService.create(management);

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(eventId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable final int id, @RequestBody final Event event){
        eventService.updateEvent(id, event);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable final int id){
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok().body(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable final int id){
        Event event = eventService.findById(id);
        return ResponseEntity.ok().body(event);
    }

}
