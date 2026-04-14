package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.FornecedorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Integer> {
    // O Spring já cria o salvar, listar, deletar e buscar por ID sozinho aqui.
    List<FornecedorEntity> findByStatus(Integer status);
    boolean existsByNomeAndStatus(String nome, Integer status);
}