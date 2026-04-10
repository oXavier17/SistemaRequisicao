package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private Integer idFornecedor;
    private String nome;
    private Integer status;
}