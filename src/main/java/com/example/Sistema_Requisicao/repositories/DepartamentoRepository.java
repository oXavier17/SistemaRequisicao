package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.DepartamentoEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, Integer> {

    // Chamando sua Procedure de cadastro
    @Procedure(procedureName = "pc_NovoDepartamento")
    void novoDepartamento(@Param("nome") String nome); 

    // Para a VIEW, você pode criar um método que faz o SELECT nela
    @Query(value = "SELECT * FROM v_DepartamentosDetalhado", nativeQuery = true)
    List<DepartamentoEntity> listarPelaView();

    
}