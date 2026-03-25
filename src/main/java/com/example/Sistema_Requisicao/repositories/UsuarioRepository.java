package com.example.Sistema_Requisicao.repositories;

import com.example.Sistema_Requisicao.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// O Repositório Pai (O "Coringa")
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    @Procedure(procedureName = "pc_NovoUsuario")
    void novoUsuario(
        @Param("nome") String nome,
        @Param("cpf") String cpf,
        @Param("email") String email,
        @Param("senha") String senha,
        @Param("tipoPerfil") Integer tipo, // 1=Admin, 2=Func, 3=Req
        @Param("departamentoid") Integer idDepto // Pode ser null se for Admin
    );
    @Procedure(procedureName = "pc_AlterarStatus")
    void alteraStatus(
        @Param("tabela") String tabela,
        @Param("id") int id,
        @Param("novoStatus") int novoStatus
    );
}