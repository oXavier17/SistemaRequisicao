package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptRequisicaoDTO {
    private String nomeDepartamento;
    private Long total;
}