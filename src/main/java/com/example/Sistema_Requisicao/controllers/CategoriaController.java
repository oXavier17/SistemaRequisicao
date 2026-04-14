package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.CategoriaDTO;
import com.example.Sistema_Requisicao.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // 🔎 Listar todas
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodos() {
        List<CategoriaDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // ➕ Criar nova categoria
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CategoriaDTO dto) {
        try {
            CategoriaDTO nova = service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✏️ Editar categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Integer id, @RequestBody CategoriaDTO dto) {
        try {
            CategoriaDTO atualizada = service.editar(id, dto);
            return ResponseEntity.ok(atualizada);
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