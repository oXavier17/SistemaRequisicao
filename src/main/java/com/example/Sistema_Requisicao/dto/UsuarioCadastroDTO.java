package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastroDTO {
    private String nome;
    private String email;
    private String cpf;
    private String senha;           // só no cadastro
    private Integer tipoPerfil;
    private Integer departamentoId;
}