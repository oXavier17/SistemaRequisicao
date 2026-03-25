package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.RequisicaoDTO;
import com.example.Sistema_Requisicao.entities.EstoqueCriticoEntity;
import com.example.Sistema_Requisicao.entities.RequisicaoEntity;
import com.example.Sistema_Requisicao.repositories.EstoqueCriticoRepository;
import com.example.Sistema_Requisicao.repositories.RequisicaoRepository;
import com.example.Sistema_Requisicao.services.RequisicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

    @Autowired
    private RequisicaoService service;

    @Autowired
    private RequisicaoRepository repository;

    // Listar todas as requisições
    @GetMapping
    public ResponseEntity<List<RequisicaoEntity>> listar() {
        List<RequisicaoEntity> lista = repository.findAll();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public String criar(@RequestBody RequisicaoDTO dto) {
        service.criarRequisicao(dto);
        return "Requisição enviada com sucesso para o SQL Server!";
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> mudaStatus(
            @PathVariable Integer id, 
            @RequestParam Integer idFuncionario,
            @RequestParam Integer novoStatus) {
        
        try {
            repository.finalizarEntrega(id, idFuncionario, novoStatus);
            return ResponseEntity.ok("Status da requisição " + id + " alterado para " + novoStatus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RestController
    @RequestMapping("/alerta")
    public class StatusController {

        @Autowired
        private EstoqueCriticoRepository repository;

        @GetMapping
        public ResponseEntity<List<EstoqueCriticoEntity>> listar() {
            // Geralmente listamos apenas os que NÃO estão resolvidos para o setor de compras
            return ResponseEntity.ok(repository.findAll()); //Mostrar todos os itens da tabela
            //return ResponseEntity.ok(repository.findByStatus("PENDENTE"));  //Mostrar apenas os pendentes
        }
    }
}