package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMaterial")
    private Integer idMaterial;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private Integer estoqueAtual = 0; // Bom colocar o default aqui também

    @Column(nullable = false)
    private Integer estoqueMin = 0;

    @Column(nullable = false)
    private Integer status = 1;

    // A MÁGICA ACONTECE AQUI:
    @ManyToOne // Muitos materiais para uma categoria
    @JoinColumn(name = "categoriaId", nullable = false) // Nome da FK no banco
    private CategoriaEntity categoria; 

    @Column(nullable = false, length = 50)
    private String unidade;
}