package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.EstoqueCriticoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueCriticoRepository extends JpaRepository<EstoqueCriticoEntity, Integer> {
    // O nome do método deve ser exatamente igual ao atributo na Entity
    List<EstoqueCriticoEntity> findByStatusAlerta(String statusAlerta);
}