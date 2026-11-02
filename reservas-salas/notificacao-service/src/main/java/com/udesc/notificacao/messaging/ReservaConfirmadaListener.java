package com.udesc.notificacao.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReservaConfirmadaListener {

    @RabbitListener(queues = "notificacoes.queue")
    public void aoConfirmarReserva(Map<String, Object> evento) {
        System.out.println("[notificacao] Reserva confirmada, avisando usuario: " + evento);
    }
}
