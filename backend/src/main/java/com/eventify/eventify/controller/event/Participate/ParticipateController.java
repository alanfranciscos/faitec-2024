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
    public ResponseEntity<List<Participate>> getEntities(){
        List<Participate> participates = participateService.findAll();
        return ResponseEntity.ok().body(participates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participate> getEntityById(@PathVariable final int id){
        Participate participate = participateService.findById(id);
        return ResponseEntity.ok().body(participate);
    }

    @PostMapping()
    public ResponseEntity<Participate> createEntity(@RequestBody final Participate data){
        int id = participateService.create(data);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntity(@PathVariable final int id, @RequestBody final Participate data){
        participateService.update(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Participate> deleteEntity(@PathVariable final int id){
        EventListResponse response = eventService.listPaginatedFromUser(6,0);

        participateService.deleteByUserEvents(id, response);
        return ResponseEntity.noContent().build();
    }
}
