package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Fornecedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFornecedor")
    private Integer idFornecedor;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private Integer status = 1;
}
