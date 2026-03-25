package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Materiais") // Nome exato da tabela no SQL Server
@Data // Faz os Getters/Setters e toString
@NoArgsConstructor // Faz o construtor vazio (o Spring exige este)
@AllArgsConstructor // Faz o construtor com todos os campos (substitui o seu manual)
public class MaterialEntity {

    @Id // Define que é chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity do SQL Server
    @Column(name = "idMaterial") // Nome exato da coluna no seu CREATE TABLE
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nome;

    private Integer estoqueAtual;
    private Integer estoqueMin;

    @Column(length = 10)
    private String unidadeMedida;
    
    // O tipo 'money' do SQL mapeia bem para Double ou BigDecimal
    private Double preco; 

    private Integer status;
}