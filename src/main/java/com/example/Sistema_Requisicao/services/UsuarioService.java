package com.example.Sistema_Requisicao.services;
/* 
import com.example.Sistema_Requisicao.entities.UsuarioEntity;
import com.example.Sistema_Requisicao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioEntity realizarLogin(String email, String senha) {
        // Busca o usuário pelo email
        return repository.findByEmail(email)
                .filter(u -> u.getSenha().equals(senha)) // Verifica se a senha bate
                .filter(u -> u.getStatus() == 1)         // Verifica se está ativo
                .orElseThrow(() -> new RuntimeException("E-mail, senha inválidos ou usuário inativo!"));
    }
    
    public UsuarioEntity salvarNovoUsuario(UsuarioEntity usuario) {
        if (repository.existsByCpf(usuario.getCpf())) {
            throw new RuntimeException("Este CPF já está cadastrado!");
        }
        return repository.save(usuario);
    }
}*/