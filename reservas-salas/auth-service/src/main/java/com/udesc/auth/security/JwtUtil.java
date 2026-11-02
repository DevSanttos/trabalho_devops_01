package com.udesc.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Gera tokens JWT com uma chave compartilhada (HS256)
 * A API Gateway usa a mesma chave (JwtValidator) para validar o token
 * sem precisar chamar de volta o auth-service a cada requisição
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // a criação de tokens é somente aqui. De resto é lá no Gateway
    public String gerarToken(String username) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
