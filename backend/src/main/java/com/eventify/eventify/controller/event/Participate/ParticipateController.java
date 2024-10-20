package com.eventify.eventify.controller.event.Participate;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.port.service.event.EventService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("api/v1/participate")
@RequiredArgsConstructor
public class ParticipateController {
    private final ParticipateService participateService;
    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<List<Participate>> getParticipates(){
        List<Participate> participates = participateService.findAll();
        return ResponseEntity.ok().body(participates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participate> getParticipateById(@PathVariable final int id){
        Participate participate = participateService.findById(id);
        return ResponseEntity.ok().body(participate);
    }

//    @PostMapping()
//    public ResponseEntity<Participate> createParticipate(@RequestBody final Participate data){
    @PostMapping()
    public ResponseEntity<Participate> createParticipate(
                                                         @RequestParam(value = "event_id", required = false) int event_id,
                                                         @RequestParam(value = "email", required = false) String email
    ){

        int id = participateService.inviteMember(event_id, email);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateParticipate(@PathVariable final int id, @RequestBody final Participate data){
        participateService.update(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Participate> deleteParticipate(@PathVariable final int id){
        EventListResponse response = eventService.listPaginatedFromUser(6,0);

        participateService.deleteByUserEvents(id, response);
        return ResponseEntity.noContent().build();
    }
}
