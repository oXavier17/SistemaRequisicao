package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MovimentoEstoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMovimento")
    private Integer idMovimento;

    @ManyToOne 
    @JoinColumn(name = "materialId", nullable = false)
    private MaterialEntity material; 

    // Aqui é importante: nullable = true, pois Entradas não têm requisição
    @ManyToOne 
    @JoinColumn(name = "requisicaoId", nullable = true)
    private RequisicaoEntity requisicao; 

    @Column(nullable = false)
    private Integer tipo; // 0-ENTRADA, 1-SAIDA

    @Column(nullable = false)
    private Integer quantidade;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco; // Usamos BigDecimal para dinheiro/decimal

    private LocalDateTime dataMovimento = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "fornecedorId", nullable = true)
    private FornecedorEntity fornecedor;

    @Column(length = 150)
    private String observacao;
}