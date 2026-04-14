package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.dto.DeptRequisicaoDTO;
import com.example.Sistema_Requisicao.entities.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisicaoRepository extends JpaRepository<RequisicaoEntity, Integer> {

    // Exemplo de busca customizada: Todas as requisições de um departamento
    List<RequisicaoEntity> findByDepartamentoIdDepartamento(Integer deptoId);

    Long countByStatus(Integer status);
    @Query("SELECT new com.example.Sistema_Requisicao.dto.DeptRequisicaoDTO(d.nome, COUNT(r)) " +
        "FROM RequisicaoEntity r JOIN r.departamento d " +
        "GROUP BY d.nome ORDER BY COUNT(r) DESC")
    List<DeptRequisicaoDTO> countByDepartamento();
    List<RequisicaoEntity> findTop5ByOrderByDataRequisicaoDesc();
}