package com.udesc.auth.service;

import com.udesc.auth.dto.LoginRequest;
import com.udesc.auth.dto.RegisterRequest;
import com.udesc.auth.exception.CredenciaisInvalidasException;
import com.udesc.auth.exception.UsuarioJaExisteException;
import com.udesc.auth.model.Usuario;
import com.udesc.auth.repository.UsuarioRepository;
import com.udesc.auth.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Regra de negocio aqui: nao vai permitir dois usuarios com o mesmo username
    public void registrar(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new UsuarioJaExisteException("Usuario '" + request.getUsername() + "' ja existe");
        }
        Usuario usuario = new Usuario(request.getUsername(), passwordEncoder.encode(request.getSenha()));
        usuarioRepository.save(usuario);
    }

    // Regra de negocio: nao revelar se o erro foi usuario ou senha
    public String login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CredenciaisInvalidasException("Usuario ou senha invalidos"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Usuario ou senha invalidos");
        }

        return jwtUtil.gerarToken(usuario.getUsername());
    }
}
