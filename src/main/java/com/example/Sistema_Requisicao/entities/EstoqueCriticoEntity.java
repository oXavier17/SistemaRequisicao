package com.example.Sistema_Requisicao.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data; // O Lombok faz o trabalho sujo

@Entity
@Table(name = "EstoqueCritico")
@Data
public class EstoqueCriticoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlerta") 
    private Integer idAlerta;
    
    @Column(name = "materialId")
    private Integer materialId;

    private String nomeMaterial;
    private Integer estoqueAtual;
    private Integer estoqueMin;

    private LocalDateTime dataGeracao = LocalDateTime.now();

    @Column(name = "statusAlerta") // Nome exato da coluna no SQL
    private String status = "PENDENTE";
}