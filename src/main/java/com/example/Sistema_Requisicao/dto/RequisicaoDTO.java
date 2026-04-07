package com.example.Sistema_Requisicao.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoDTO {
    private Integer idRequisicao;
    private LocalDateTime dataRequisicao;
    private LocalDateTime dataEnvio;
    private Integer status;
    private String observacao;
    private Integer requisitanteId;
    private String nomeRequisitante;
    private Integer funcionarioId;
    private Integer departamentoId;
    private String nomeDepartamento;
    private List<ItemRequisicaoDTO> itens;
}