package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.AdministradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface AdministradorRepository extends JpaRepository<AdministradorEntity, Integer> {}

