package com.example.Sistema_Requisicao.dto;

import lombok.Data;
import java.util.List;

@Data
public class RequisicaoDTO {
    private Integer idRequisitante;
    private String observacao;
    private Integer idDepartamento;
    private List<ItemDTO> itens;

    @Data
    public static class ItemDTO {
        private Integer materialId;
        private Integer quantidade;
    }
}