package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoCadastroDTO {
    private Integer materialId;
    private Integer tipo;           // 0=ENTRADA, 1=SAIDA
    private Integer quantidade;
    private Integer fornecedorId;   // null se for saída
    private BigDecimal preco;       // null se for saída
    private String observacao;
}