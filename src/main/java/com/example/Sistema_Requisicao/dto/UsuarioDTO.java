package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String cpf;
    private Integer tipoPerfil;
    private Integer departamentoId;
    private String departamentoNome;
    private Integer status;
}