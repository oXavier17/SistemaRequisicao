package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.*;
import com.example.Sistema_Requisicao.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // Permite que o React acesse a API
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // POST e PUT recebem UsuarioCadastroDTO (com senha)
    // GET retorna UsuarioDTO (sem senha)
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioCadastroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Integer id, @RequestBody UsuarioCadastroDTO dto) {
        try {
            return ResponseEntity.ok(service.editar(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Integer id) {
        try {
            service.alterarStatus(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}