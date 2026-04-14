package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.MaterialDTO;
import com.example.Sistema_Requisicao.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    @Autowired
    private MaterialService service;

    @GetMapping
    public ResponseEntity<List<MaterialDTO>> listarTodos(
            @RequestParam(defaultValue = "false") boolean todos) {
        if (todos) {
            return ResponseEntity.ok(service.listarTodosComInativos());
        }
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MaterialDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Integer id, @RequestBody MaterialDTO dto) {
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