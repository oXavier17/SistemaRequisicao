package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.DepartamentoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, Integer> {
    // O Spring já cria o salvar, listar, deletar e buscar por ID sozinho aqui.
    List<DepartamentoEntity> findByStatus(Integer status);
    boolean existsByNomeAndStatus(String nome, Integer status);
}