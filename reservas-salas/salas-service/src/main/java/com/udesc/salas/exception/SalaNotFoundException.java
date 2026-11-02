package com.udesc.salas.exception;

public class SalaNotFoundException extends RuntimeException {
    public SalaNotFoundException(Long id) {
        super("Sala " + id + " nao encontrada");
    }
}
