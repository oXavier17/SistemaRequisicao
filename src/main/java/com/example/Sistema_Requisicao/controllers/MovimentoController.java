package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.*;
import com.example.Sistema_Requisicao.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentos")
public class MovimentoController {

    @Autowired
    private MovimentoService service;

    @GetMapping
    public ResponseEntity<List<MovimentoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // POST recebe MovimentoCadastroDTO — não o MovimentoDTO de exibição
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody MovimentoCadastroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}