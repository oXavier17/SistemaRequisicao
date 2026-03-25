package com.example.Sistema_Requisicao.controllers;

import com.example.Sistema_Requisicao.dto.UsuarioDTO;
import com.example.Sistema_Requisicao.entities.UsuarioEntity;
import com.example.Sistema_Requisicao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listagem Geral (Traz Admins, Funcionários e Requisitantes)
    @GetMapping
    public List<UsuarioEntity> listarTodos() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public String cadastrar(@RequestBody UsuarioDTO dto) {

        usuarioRepository.novoUsuario(
                dto.getNome(),
                dto.getCpf(),
                dto.getEmail(),
                dto.getSenha(),
                dto.getTipoPerfil(),
                dto.getDepartamentoId()
        );

        return "Usuário cadastrado com sucesso!";
    }

    @RestController
    @RequestMapping("/admin")
    public class StatusController {

        @Autowired
        private UsuarioRepository repository; // Usamos o repo que tem a @Procedure

        @PatchMapping("/alterar-status")
        public ResponseEntity<String> alteraStatus(
                @RequestParam String tabela,
                @RequestParam Integer id,
                @RequestParam Integer novoStatus) {
            
            // Chama a procedure genérica que criamos no SQL
            repository.alteraStatus(tabela, id, novoStatus);
            
            String acao = (novoStatus == 1) ? "ativado" : "inativado";
            return ResponseEntity.ok(tabela + " " + id + " foi " + acao + " com sucesso!");
        }
    }
}