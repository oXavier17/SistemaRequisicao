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

    // Exemplo de busca customizada: Todas as requisições de um departamento
    // List<RequisicaoEntity> findByDepartamentoId(Integer deptoId);

    // Aqui chamaremos sua procedure de baixar estoque no futuro
    @Modifying(clearAutomatically = true) // <--- Isso força o Spring a atualizar as entidades
    @Transactional
    @Procedure(name = "STP_FinalizarRequisicao")
    void finalizarRequisicao(
        @Param("idRequisicao") Integer idReq, 
        @Param("idFuncionario") Integer idFunc
    );
}