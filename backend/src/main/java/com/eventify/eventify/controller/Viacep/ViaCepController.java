package com.eventify.eventify.controller.Viacep;

import com.eventify.eventify.dto.locations.ViaCepDto;
import com.eventify.eventify.port.service.viacep.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class ViaCepController {

    private final ViaCepService viaCepService;

    @Autowired
    public ViaCepController(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepDto> getAdressByPostalCode(@PathVariable String cep){
        ViaCepDto address = viaCepService.getAdressByPostalCode(cep);
        if(address == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }

}
