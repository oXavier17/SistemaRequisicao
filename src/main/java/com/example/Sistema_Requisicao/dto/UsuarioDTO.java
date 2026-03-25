package com.example.Sistema_Requisicao.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private Integer tipoPerfil;      // 1=Adm, 2=Func, 3=Req
    private Integer departamentoId;  // só usado se tipoPerfil = 2 ou 3
}