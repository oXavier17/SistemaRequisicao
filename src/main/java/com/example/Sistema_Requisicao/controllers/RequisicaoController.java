package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.RequisicaoDTO;
import com.example.Sistema_Requisicao.services.RequisicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

    @Autowired
    private RequisicaoService service;

    @GetMapping
    public ResponseEntity<List<RequisicaoDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody RequisicaoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH para atualizar só o status — ex: Aberta → Em Separação
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        try {
            Integer novoStatus = body.get("status");
            if (novoStatus == null) {
                return ResponseEntity.badRequest().body("Campo 'status' é obrigatório.");
            }
            return ResponseEntity.ok(service.atualizarStatus(id, novoStatus));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH para editar quantidade de um item específico
    @PatchMapping("/{requisicaoId}/itens/{materialId}")
    public ResponseEntity<?> editarQuantidadeItem(
            @PathVariable Integer requisicaoId,
            @PathVariable Integer materialId,
            @RequestBody Map<String, Integer> body) {
        try {
            Integer quantidade = body.get("quantidade");
            if (quantidade == null) {
                return ResponseEntity.badRequest().body("Campo 'quantidade' é obrigatório.");
            }
            return ResponseEntity.ok(service.editarQuantidadeItem(requisicaoId, materialId, quantidade));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}