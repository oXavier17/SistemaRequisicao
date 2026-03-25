package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Requisicao")
@Data
public class RequisicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRequisicao")
    private Integer id;

    // Quem solicitou (Pode ser um Requisitante ou Funcionario)
    @ManyToOne
    @JoinColumn(name = "requisitanteId")
    private UsuarioEntity solicitante;

    // Quem entregou (Pode ser nulo enquanto estiver pendente)
    @ManyToOne
    @JoinColumn(name = "funcionarioId")
    private UsuarioEntity atendidoPor;

    private LocalDateTime dataRequisicao = LocalDateTime.now();
    
    @Column(name = "dataEntrega")
    private LocalDateTime dataEntrega;
    
    // Status: 1-Pendente, 2-Finalizada, 0-Cancelada
    private Integer status;

    // Relacionamento Um-para-Muitos: Uma requisição tem vários itens
    // O 'mappedBy' aponta para o nome do campo dentro da classe ItemRequisicaoEntity
    @OneToMany(mappedBy = "requisicao", fetch = FetchType.EAGER) // EAGER força o carregamento dos itens
    @JsonManagedReference
    private List<ItemRequisicaoEntity> itens;

    @Transient
    private String statusDescricao;
}