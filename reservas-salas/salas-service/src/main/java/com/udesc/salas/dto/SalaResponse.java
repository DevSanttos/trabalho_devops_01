package com.udesc.salas.dto;

import com.udesc.salas.model.Sala;

public class SalaResponse {

    private Long id;
    private String nome;
    private Integer capacidade;
    private boolean disponivel;

    public SalaResponse(Sala sala) {
        this.id = sala.getId();
        this.nome = sala.getNome();
        this.capacidade = sala.getCapacidade();
        this.disponivel = sala.isDisponivel();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
