package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    // O Spring já cria o salvar, listar, deletar e buscar por ID sozinho aqui.
    boolean existsByNome(String nome);
    // O segredo está no 'Not': ele verifica se existe o nome, mas o ID é diferente do atual
    boolean existsByNomeAndIdCategoriaNot(String nome, Integer id);
}