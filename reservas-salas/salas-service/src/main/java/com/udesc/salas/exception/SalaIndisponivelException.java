package com.udesc.salas.exception;

public class SalaIndisponivelException extends RuntimeException {
    public SalaIndisponivelException(Long id) {
        super("Sala " + id + " nao esta disponivel no momento");
    }
}
