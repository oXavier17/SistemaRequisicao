package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {
}
