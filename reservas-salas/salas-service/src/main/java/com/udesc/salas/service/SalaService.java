package com.udesc.salas.service;

import com.udesc.salas.dto.SalaRequest;
import com.udesc.salas.exception.SalaIndisponivelException;
import com.udesc.salas.exception.SalaNotFoundException;
import com.udesc.salas.model.Sala;
import com.udesc.salas.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new SalaNotFoundException(id));
    }

    public Sala criar(SalaRequest request) {
        Sala sala = new Sala(request.getNome(), request.getCapacidade());
        return salaRepository.save(sala);
    }

    public void deletar(Long id) {
        salaRepository.deleteById(id);
    }

    public Sala reservar(Long id) {
        Sala sala = buscarPorId(id);
        if (!sala.isDisponivel()) {
            throw new SalaIndisponivelException(id);
        }
        sala.setDisponivel(false);
        return salaRepository.save(sala);
    }

    public Sala liberar(Long id) {
        Sala sala = buscarPorId(id);
        sala.setDisponivel(true);
        return salaRepository.save(sala);
    }
}
