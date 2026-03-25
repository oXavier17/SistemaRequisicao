package com.example.Sistema_Requisicao.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Administrador")
@PrimaryKeyJoinColumn(name = "idUsuario")
@Data
@EqualsAndHashCode(callSuper = true) // Importante para classes com herança
public class AdministradorEntity extends UsuarioEntity {

}