package com.github.jhonathampro.api_registro_jhonatham_559114_m1.controller;

import com.github.jhonathampro.api_registro_jhonatham_559114_m1.dto.RegistroMeteorologicoRequestDTO;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.dto.RegistroMeteorologicoResponseDTO;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.service.RegistroMeteorologicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroMeteorologicoController {

    @Autowired
    private RegistroMeteorologicoService registroService;

    @GetMapping
    public ResponseEntity<List<RegistroMeteorologicoResponseDTO>> getAllRegistros() {

        List<RegistroMeteorologicoResponseDTO> list = registroService.findAllRegistros();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroMeteorologicoResponseDTO> getRegistroById(@PathVariable Long id) {

        RegistroMeteorologicoResponseDTO registroDTO = registroService.findRegistroById(id);

        return ResponseEntity.ok(registroDTO);
    }

    @PostMapping
    public ResponseEntity<RegistroMeteorologicoResponseDTO> createRegistro(@RequestBody
                                                                           @Valid
                                                                           RegistroMeteorologicoRequestDTO inputDTO) {

        RegistroMeteorologicoResponseDTO registroDTO = registroService.saveRegistro(inputDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(registroDTO.getRegistroId())
                .toUri();

        return ResponseEntity.created(uri).body(registroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroMeteorologicoResponseDTO> updateRegistro(@PathVariable Long id,
                                                                           @RequestBody @Valid
                                                                           RegistroMeteorologicoRequestDTO inputDTO) {

        RegistroMeteorologicoResponseDTO registroDTO = registroService.updateRegistro(id, inputDTO);

        return ResponseEntity.ok(registroDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Long id) {

        registroService.deleteRegistroById(id);

        return ResponseEntity.noContent().build();
    }
}
