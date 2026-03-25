package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.RequisicaoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisicaoRepository extends JpaRepository<RequisicaoEntity, Integer> {

    @Modifying(clearAutomatically = true) // <--- Isso força o Spring a atualizar as entidades
    @Transactional
    // Procedure que você já tinha para finalizar (dar baixa)
    @Procedure(procedureName = "pc_FinalizarEntrega")
    void finalizarEntrega(
        @Param("idRequisicao") Integer idRequisicao,
        @Param("idFuncionario") Integer idFuncionario,
        @Param("novoStatus") Integer novoStatus
    );
}