package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import com.example.Sistema_Requisicao.dto.LoginDTO;
import com.example.Sistema_Requisicao.dto.LoginResponseDTO;
import com.example.Sistema_Requisicao.entities.UsuarioEntity;
import com.example.Sistema_Requisicao.repositories.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginDTO dto) {
        UsuarioEntity usuario = usuarioRepository
                .findByEmailAndStatus(dto.getEmail(), 1)
                .orElseThrow(() -> new BadCredentialsException("E-mail ou senha incorretos."));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("E-mail ou senha incorretos.");
        }

        String token = jwtService.gerarToken(
            usuario.getEmail(),
            usuario.getIdUsuario(),
            usuario.getTipoPerfil()
        );

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setIdUsuario(usuario.getIdUsuario());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setTipoPerfil(usuario.getTipoPerfil());

        if (usuario.getDepartamento() != null) {
            response.setDepartamentoId(usuario.getDepartamento().getIdDepartamento());
            response.setDepartamentoNome(usuario.getDepartamento().getNome());
        }

        return response;
    }
}