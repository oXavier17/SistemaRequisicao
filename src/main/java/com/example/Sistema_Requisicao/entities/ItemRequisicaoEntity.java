package com.example.Sistema_Requisicao.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ItemRequisicao")
@Data
public class ItemRequisicaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "requisicaoId")
    @JsonBackReference
    private RequisicaoEntity requisicao; // SEM @GeneratedValue aqui!

    @ManyToOne
    @JoinColumn(name = "materialId")
    private MaterialEntity material; // SEM @GeneratedValue aqui!

    private Integer quantidade;
}