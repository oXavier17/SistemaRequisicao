package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.MovimentoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoRepository extends JpaRepository<MovimentoEntity, Integer> {
    // O Spring já cria o salvar, listar, deletar e buscar por ID sozinho aqui.
    List<MovimentoEntity> findAllByOrderByDataMovimentoDesc();
}