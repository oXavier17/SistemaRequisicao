package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Requisicao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequisicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRequisicao")
    private Integer idRequisicao;

    private LocalDateTime dataRequisicao = LocalDateTime.now();
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private Integer status; // 1-Aberta, 2-Separação, 3-Pronta, 4-Entregue, 5-Cancelada

    @Column(length = 200)
    private String observacao;

    // QUEM PEDIU (Sempre um Usuario com tipo_perfil = 3)
    @ManyToOne
    @JoinColumn(name = "requisitanteId", nullable = false)
    private UsuarioEntity requisitante;

    // QUEM ATENDEU (Um Usuario com tipo_perfil = 2, pode ser null enquanto estiver aberta)
    @ManyToOne
    @JoinColumn(name = "funcionarioId", nullable = true)
    private UsuarioEntity funcionario;

    // DEPARTAMENTO DE DESTINO
    @ManyToOne
    @JoinColumn(name = "departamentoId", nullable = true)
    private DepartamentoEntity departamento;

    // RELACIONAMENTO COM OS ITENS (Opcional, mas ajuda muito no GET do Front-end)
    @OneToMany(mappedBy = "requisicao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ItemRequisicaoEntity> itens = new ArrayList<>();
}