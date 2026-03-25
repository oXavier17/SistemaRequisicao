package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {

    // Caso você queira usar a procedure de cadastro:
    @Procedure(procedureName = "pc_NovoMaterial")
    void novoMaterial(
        @Param("nome") String nome,
        @Param("estoqueAtual") Integer estoqueAtual,
        @Param("estoqueMin") Integer estoqueMin,
        @Param("unidadeMedida") String unidadeMedida,
        @Param("preco") Double preco
    );
}