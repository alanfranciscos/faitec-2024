package com.eventify.eventify.controller.Viacep;

import com.eventify.eventify.dto.locations.ViaCepDto;
import com.eventify.eventify.port.service.viacep.ViaCepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class ViaCepController {

    // Exemplo de requisição
    // http://localhost:8080/cep/37540000

    private final ViaCepService viaCepService;

    public ViaCepController(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepDto> findAdressByPostalCode(@PathVariable String cep){
        ViaCepDto address = viaCepService.findAdressByPostalCode(cep);
        if(address == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(address);
    }
}
