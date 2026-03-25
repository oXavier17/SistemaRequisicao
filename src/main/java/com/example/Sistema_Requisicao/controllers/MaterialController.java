package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.entities.MaterialEntity;
import com.example.Sistema_Requisicao.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    private MaterialRepository repository;

    // Listar todos os materiais
    @GetMapping
    public List<MaterialEntity> listar() {
        return repository.findAll();
    }

    // Cadastrar material (pelo padrão do Spring .save)
    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody MaterialEntity material) {

        repository.novoMaterial(
            material.getNome(),
            material.getEstoqueAtual(),
            material.getEstoqueMin(),
            material.getUnidadeMedida(),
            material.getPreco()
        );

        return ResponseEntity.ok("Material processado com sucesso!");
    }
}