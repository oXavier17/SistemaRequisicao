package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime; // Importante para a data!

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoDTO {
    private Integer idMovimento;       // ← consistência com os outros
    private Integer tipo;              // 0=ENTRADA, 1=SAIDA
    private Integer quantidade;
    private LocalDateTime dataMovimento;
    private String materialNome;
    private String fornecedorNome;
    private Integer requisicaoId;
    private BigDecimal preco;          // ← faltava, só entrada
    private String observacao;         // ← faltava
}