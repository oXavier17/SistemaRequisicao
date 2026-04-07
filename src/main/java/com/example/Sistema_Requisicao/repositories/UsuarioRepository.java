package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.UsuarioEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    
    // O Spring entende que deve fazer um: SELECT * FROM Usuario WHERE email = ?
    Optional<UsuarioEntity> findByEmail(String email);
    
    Optional<UsuarioEntity> findByCpf(String cpf);
}