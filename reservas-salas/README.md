# Trabalho 1 – devops: Reservas de Salas

## Padrões aplicados
- **API Gateway**: porta única de entrada, roteia `/api/{auth,salas,reservas}/**`
  e valida o JWT antes de repassar a chamada.
- **Saga (orchestration-based)**: o `reservas-service` orquestra a transação —
  reserva a sala via REST no `salas-service` e, se der certo, publica um evento
  no RabbitMQ. Se a publicação falhar, ele **compensa**: libera a sala de volta
  e cancela a reserva. Veja `ReservaService.criarReserva`.

## Serviços
| Serviço | Porta | Papel |
|---------|-------|-------|
| gateway-service | 8080 | API Gateway + validação de JWT |
| auth-service | 8081 | login/cadastro, emite JWT |
| salas-service | 8082 | cadastro/disponibilidade de salas |
| reservas-service | 8083 | **microsserviço de domínio individual** + orquestrador da Saga |
| notificacao-service | 8084 | consumidor simples de fila (RabbitMQ), só loga a notificação |
| rabbitmq | 5672/15672 | mensageria (usada só no passo assíncrono da Saga) |

Comunicação: **REST**
**RabbitMQ** só no passo final

## Rodando
```
docker compose up --build
```

1. `POST /api/auth/registrar` `{ "username": "joao", "senha": "123456" }`
2. `POST /api/auth/login` → retorna `token`
3. `POST /api/salas` (usar o token) `{ "nome": "Sala 101", "capacidade": 20 }`
4. `POST /api/reservas` `{ "salaId": 1, "usuario": "joao", "inicio": "...", "fim": "..." }`
   → dispara a Saga: reserva a sala e publica o evento de confirmação.

## Testes automatizados
Feitos só no **reservas-service** 

## Entrega
- A aplicação deve ser entregue utilizando containers Docker.
- Deve ser criado um Dockerfile para cada microsserviço.
- Deve ser disponibilizado um docker-compose que permita executar toda a
aplicação de forma integrada.


cadastrar e autenticar usuários;
cadastrar salas;
verificar salas disponíveis;
criar reservas;
confirmar ou cancelar reservas;
publicar eventos de confirmação;
simular notificações por meio de RabbitMQ.
A aplicação foi construída com Java 17, Spring Boot 3.2.5, Spring Cloud Gateway, Spring Data JPA, H2, JWT e RabbitMQ.

o cliente acessa gateway pela porta 8080

A comunicação entre reservas-service e salas-service é síncrona, usando REST.

Saga orchestration-based - ReservaService.criarReserva