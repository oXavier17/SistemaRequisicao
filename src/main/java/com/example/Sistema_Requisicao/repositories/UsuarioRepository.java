package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.UsuarioEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByCpf(String cpf);
    Optional<UsuarioEntity> findByEmailAndStatus(String email, Integer status);
    List<UsuarioEntity> findByStatus(Integer status);
    Long countByStatus(Integer status);
    Long countByTipoPerfilAndStatus(Integer tipoPerfil, Integer status);
}