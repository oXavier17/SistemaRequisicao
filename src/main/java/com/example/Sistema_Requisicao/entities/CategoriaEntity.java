package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.Data; // O Lombok faz o trabalho sujo

@Entity
@Table(name = "Categoria")
@Data
public class CategoriaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria") // Mapeia exatamente o nome da coluna no SQL
    private Integer idCategoria;
    
    @Column(name = "nome") // Mapeia a coluna 'nome' do banco
    private String nome;

    private Integer status = 1;
}