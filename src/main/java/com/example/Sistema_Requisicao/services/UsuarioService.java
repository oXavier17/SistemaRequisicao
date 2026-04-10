package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import com.example.Sistema_Requisicao.dto.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO salvar(UsuarioCadastroDTO dto) throws Exception {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("Este e-mail já está cadastrado.");
        }
        if (usuarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new Exception("Este CPF já está cadastrado.");
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setSenha(dto.getSenha()); // futuramente: BCryptPasswordEncoder
        entity.setTipoPerfil(dto.getTipoPerfil());
        entity.setStatus(1);

        if (dto.getDepartamentoId() != null) {
            DepartamentoEntity depto = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new Exception("Departamento não encontrado."));
            entity.setDepartamento(depto);
        }

        return convertToDTO(usuarioRepository.save(entity));
    }

    public UsuarioDTO editar(Integer id, UsuarioCadastroDTO dto) throws Exception {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));

        // Verifica email duplicado ignorando o próprio usuário
        usuarioRepository.findByEmail(dto.getEmail())
                .filter(u -> !u.getIdUsuario().equals(id))
                .ifPresent(u -> { throw new RuntimeException("E-mail já cadastrado."); });

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setTipoPerfil(dto.getTipoPerfil());

        // Só atualiza senha se foi enviada
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            entity.setSenha(dto.getSenha());
        }

        if (dto.getDepartamentoId() != null) {
            DepartamentoEntity depto = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new Exception("Departamento não encontrado."));
            entity.setDepartamento(depto);
        } else {
            entity.setDepartamento(null); // Administrador não tem departamento
        }

        return convertToDTO(usuarioRepository.save(entity));
    }

    public void alterarStatus(Integer id) throws Exception {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario não encontrado."));
        
        // Se for 1, vira 0. Se for 0 (ou qualquer outra coisa), vira 1.
        int novoStatus = (entity.getStatus() == 1) ? 0 : 1;
        
        entity.setStatus(novoStatus);
        usuarioRepository.save(entity);
    }

    private UsuarioDTO convertToDTO(UsuarioEntity entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(entity.getIdUsuario());    // ← era setId()
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setCpf(entity.getCpf());
        dto.setTipoPerfil(entity.getTipoPerfil());
        dto.setStatus(entity.getStatus());          // ← faltava

        if (entity.getDepartamento() != null) {
            dto.setDepartamentoId(entity.getDepartamento().getIdDepartamento()); // ← era getId()
            dto.setDepartamentoNome(entity.getDepartamento().getNome());
        }
        return dto;
    }
}