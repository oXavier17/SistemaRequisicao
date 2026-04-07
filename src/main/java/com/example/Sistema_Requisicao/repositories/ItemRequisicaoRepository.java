package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.ItemRequisicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRequisicaoRepository extends JpaRepository<ItemRequisicaoEntity, Integer> {
    // Caso você precise listar todos os itens de uma requisição específica no futuro
    List<ItemRequisicaoEntity> findByRequisicaoId(Integer requisicaoId);
    // O Spring navega: item -> requisicao -> idRequisicao E item -> material -> idMaterial
    Optional<ItemRequisicaoEntity> findByRequisicaoIdAndMaterialId(Integer idRequisicao, Integer idMaterial);
}