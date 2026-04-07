package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.MaterialDTO;
import com.example.Sistema_Requisicao.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiais")
@CrossOrigin(origins = "*")
public class MaterialController {

    @Autowired
    private MaterialService service;

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody MaterialDTO dto) {
        try {
            service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // Se a categoria não existir ou o nome for duplicado, cai aqui
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}