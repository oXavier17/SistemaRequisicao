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
    
    @ManyToOne
    @JoinColumn(name = "materialId", nullable = false)
    private MaterialEntity material;

    private LocalDateTime dataGeracao = LocalDateTime.now();

    @Column(name = "dataResolucao", nullable = true)
    private LocalDateTime dataResolucao;

    @Column(name = "statusAlerta") // Nome exato da coluna no SQL
    private String statusAlerta = "PENDENTE";
}