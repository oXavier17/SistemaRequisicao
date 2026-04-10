package com.example.Sistema_Requisicao.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueCriticoDTO {
    
    private Integer idAlerta;      // ID da tabela EstoqueCritico
    private Integer materialId;    // ID do Material
    private String materialNome;
    private String categoriaNome;
    private Integer estoqueAtual;
    private Integer estoqueMin;
    private Integer falta;         // Cálculo: (Minimo - Atual)
    private LocalDateTime dataGeracao; // Quando o alerta foi criado pela Trigger
    private String statusAlerta;   // PENDENTE, PEDIDO ou RESOLVIDO
}