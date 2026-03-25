package com.example.Sistema_Requisicao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore; // Importação correta
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ItemRequisicao")
@Data
public class ItemRequisicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idItem") // Mapeando o nome físico da coluna
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "requisicaoId")
    @JsonIgnore // <--- O SEGREDO ESTÁ AQUI: Impede que o item tente mostrar a requisição dentro dele
    private RequisicaoEntity requisicao;

    @ManyToOne
    @JoinColumn(name = "materialId")
    private MaterialEntity material;

    private Integer quantidade;
}