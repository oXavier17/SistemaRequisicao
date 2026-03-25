package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED) // ESSENCIAL: Diz que a herança está dividida em tabelas
@Data
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer id;

    private String nome;
    private String cpf;
    private String email;
    private String senha;

    @Column(name = "tipo_perfil")
    private Integer tipoPerfil;

    private Boolean status;

    // Campos auxiliares (Transient)
    // O @Transient diz ao Spring para NÃO procurar essa coluna no banco de dados.
    // Usamos isso para guardar as descrições daquelas suas VIEWS.
    @Transient
    private String perfilDescricao;
    
    @Transient
    private String statusDescricao;
}