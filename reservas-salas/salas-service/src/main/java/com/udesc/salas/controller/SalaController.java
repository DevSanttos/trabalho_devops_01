package com.udesc.salas.controller;

import com.udesc.salas.dto.SalaRequest;
import com.udesc.salas.dto.SalaResponse;
import com.udesc.salas.model.Sala;
import com.udesc.salas.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<SalaResponse> listar() {
        return salaService.listarTodas().stream().map(SalaResponse::new).toList();
    }

    @GetMapping("/{id}")
    public SalaResponse buscar(@PathVariable Long id) {
        return new SalaResponse(salaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SalaResponse> criar(@Valid @RequestBody SalaRequest request) {
        Sala sala = salaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SalaResponse(sala));
    }

    @PostMapping("/{id}/reservar")
    public SalaResponse reservar(@PathVariable Long id) {
        return new SalaResponse(salaService.reservar(id));
    }

    @PostMapping("/{id}/liberar")
    public SalaResponse liberar(@PathVariable Long id) {
        return new SalaResponse(salaService.liberar(id));
    }

    @DeleteMapping ("/{id}")
    public boolean deletar(@PathVariable Long id) {
        salaService.deletar(id);
        return true;
    }
}
