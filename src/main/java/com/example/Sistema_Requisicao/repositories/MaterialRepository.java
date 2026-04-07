package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {
    // O Spring já cria o salvar, listar, deletar e buscar por ID sozinho aqui.
    @Query("SELECT m FROM MaterialEntity m WHERE m.estoqueAtual < m.estoqueMin")
    List<MaterialEntity> buscarMateriaisEmEstoqueCritico();
}