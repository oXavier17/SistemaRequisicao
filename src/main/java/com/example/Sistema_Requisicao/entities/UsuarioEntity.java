package com.example.Sistema_Requisicao.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 64)
    private String senha;

    @Column(name = "tipo_perfil", nullable = false)
    private Integer tipoPerfil; // 1-ADM, 2-FUNC, 3-REQ

    private Integer status = 1;

    // Relacionamento com Departamento
    // Como ADMs podem não ter departamento, deixamos nullable = true
    @ManyToOne
    @JoinColumn(name = "departamentoId", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DepartamentoEntity departamento;

    // Dica: Método auxiliar para o Front-end saber o nome do perfil
    public String getRoleName() {
        return switch (this.tipoPerfil) {
            case 1 -> "ADMIN";
            case 2 -> "FUNCIONARIO";
            case 3 -> "REQUISITANTE";
            default -> "DESCONHECIDO";
        };
    }
}