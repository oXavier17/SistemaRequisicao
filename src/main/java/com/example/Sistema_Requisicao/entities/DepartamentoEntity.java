package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.Data; // O Lombok faz o trabalho sujo

@Entity
@Table(name = "Departamento")
@Data
public class DepartamentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDepartamento") // Mapeia exatamente o nome da coluna no SQL
    private Integer idDepartamento;
    
    @Column(name = "nome") // Mapeia a coluna 'nome' do banco
    private String nome;
    
    private Integer status;
}