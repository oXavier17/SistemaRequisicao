package com.example.Sistema_Requisicao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Sistema_Requisicao.dto.EstoqueCriticoDTO;
import com.example.Sistema_Requisicao.services.EstoqueCriticoService;

@RestController
@RequestMapping("/estoque-critico")
@CrossOrigin(origins = "*") // Permite que o React acesse a API
public class EstoqueCriticoController {

    @Autowired
    private EstoqueCriticoService service;

    @GetMapping
    public ResponseEntity<List<EstoqueCriticoDTO>> listarCriticos() {
        return ResponseEntity.ok(service.listarPendentes());
    }
}