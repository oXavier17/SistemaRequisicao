package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Integer idMaterial;
    private String nome;
    private Integer estoqueAtual;
    private Integer estoqueMin;
    private Integer status;
    private Integer categoriaId;    // Para o POST
    private String categoriaNome;   // Para o GET
    private String unidade;
}