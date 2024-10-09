package com.eventify.eventify.controller.event.Management;

import com.eventify.eventify.dto.event.management.ManagementCreateResponse;
import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.port.service.event.management.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;

    @PostMapping()
    public ResponseEntity<Management> createManagement(@RequestBody final Management management){
        int id = managementService.create(management);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}/management")
    public ResponseEntity<ManagementCreateResponse> getManagementById(@PathVariable int id) {
        Management response = managementService.findById(id);

        return ResponseEntity.ok(new ManagementCreateResponse(
                response.getId(),
                response.getParticipate_id(),
                response.getManagment_at(),
                response.getType_action()
        ));
    }

    @GetMapping("/managements")
    public ResponseEntity<List<Management>> getManagements() {
        List<Management> managements = managementService.findAll();
        return ResponseEntity.ok().body(managements);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateManagement(@PathVariable final int id, @RequestBody final Management management){
        managementService.update(id, management);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Management> deleteManagement(@PathVariable final int id){
        managementService.delete(id);
        return ResponseEntity.noContent().build();
    }

}