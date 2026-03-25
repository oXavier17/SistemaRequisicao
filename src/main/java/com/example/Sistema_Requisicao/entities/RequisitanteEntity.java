package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Requisitante")
@PrimaryKeyJoinColumn(name = "idUsuario")
@Data
@EqualsAndHashCode(callSuper = true) // Importante para classes com herança
public class RequisitanteEntity extends UsuarioEntity {

    // @Id não vai aqui! Como é herança (JOINED), o ID vem da UsuarioEntity

    // Aqui acontece a mágica do relacionamento:
    @ManyToOne // Muitos funcionários para um departamento
    @JoinColumn(name = "departamentoId") // O nome da FK na tabela Funcionario
    private DepartamentoEntity departamento;

    // Se você tiver outros campos específicos de funcionário no banco, coloque aqui
}