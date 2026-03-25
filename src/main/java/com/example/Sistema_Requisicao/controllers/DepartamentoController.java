package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.entities.DepartamentoEntity;
import com.example.Sistema_Requisicao.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository repository;

    @GetMapping
    public List<DepartamentoEntity> listar() {
        // Aqui o Spring faz o "SELECT * FROM Departamento" sozinho
        return repository.findAll();
    }

    @PostMapping
    public void cadastrarPelaProcedure(@RequestBody DepartamentoEntity depto) {
        // Aqui você chama a sua procedure passando apenas o nome
        repository.novoDepartamento(depto.getNome());
    }
}