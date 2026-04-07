package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.RequisicaoDTO;
import com.example.Sistema_Requisicao.services.RequisicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requisicoes")
@CrossOrigin(origins = "*")
public class RequisicaoController {

    @Autowired
    private RequisicaoService service;

    @GetMapping
    public ResponseEntity<List<RequisicaoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody RequisicaoDTO dto) {
        try {
            service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}