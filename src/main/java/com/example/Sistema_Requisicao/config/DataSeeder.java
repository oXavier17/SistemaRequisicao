package com.example.Sistema_Requisicao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;


@Component
public class DataSeeder implements ApplicationRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Só cria se não existir nenhum admin
        long totalAdmins = usuarioRepository.countByTipoPerfilAndStatus(1, 1);

        if (totalAdmins == 0) {
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNome("Administrador");
            admin.setCpf("000.000.000-00");
            admin.setEmail("admin@sistema.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setTipoPerfil(1);
            admin.setStatus(1);
            admin.setDepartamento(null);
            usuarioRepository.save(admin);

            System.out.println("==============================================");
            System.out.println("  ADMIN PADRÃO CRIADO:");
            System.out.println("  Email: admin@sistema.com");
            System.out.println("  Senha: admin123");
            System.out.println("  ALTERE A SENHA APÓS O PRIMEIRO ACESSO!");
            System.out.println("==============================================");
        }
    }
}