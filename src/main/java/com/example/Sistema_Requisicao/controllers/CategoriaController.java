package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.CategoriaDTO;
import com.example.Sistema_Requisicao.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias") // Define a rota base
@CrossOrigin(origins = "*") // Permite que o React acesse a API
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista); // Retorna Status 200 OK
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody CategoriaDTO dto) {
        try {
            service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna 201 Created
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Retorna 400 com o erro
        }
    }
}