package com.example.Sistema_Requisicao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueCriticoDTO {
    
    private Integer materialId;   // Útil se o usuário quiser clicar no nome e abrir o cadastro
    private String materialNome; // O que realmente importa na lista
    private String categoriaNome; // Para agrupar (ex: Limpeza, Escritório)
    private Integer estoqueAtual;
    private Integer estoqueMin;
    private Integer falta;        // Cálculo: (estoqueMinimo - estoqueAtual)
    
}