package com.example.Sistema_Requisicao.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequisicaoDTO {
    private Integer idItem;
    private Integer idMaterial;
    private String nomeMaterial;
    private Integer quantidade;
    private String nomeUnidade;
}