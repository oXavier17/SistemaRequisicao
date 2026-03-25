package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.RequisitanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitanteRepository extends JpaRepository<RequisitanteEntity, Integer> {
}

