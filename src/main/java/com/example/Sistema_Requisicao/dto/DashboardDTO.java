package com.example.Sistema_Requisicao.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalRequisicoes;
    private Long requisicoesPendentes;
    private Long totalItensEstoque;
    private Long totalUsuariosAtivos;
    private List<DeptRequisicaoDTO> requisicoesPorDepartamento;
    private List<EstoqueCriticoDTO> estoqueCritico;
    private List<RequisicaoDTO> requisicoesMaisRecentes;
}