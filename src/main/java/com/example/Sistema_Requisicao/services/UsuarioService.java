package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;

import jakarta.servlet.http.HttpServletRequest;

import com.example.Sistema_Requisicao.dto.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtService jwtService;

    // Só ativos
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findByStatus(1)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Todos incluindo inativos
    public List<UsuarioDTO> listarTodosComInativos() {
        return usuarioRepository.findAll()
                .stream()
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
        entity.setSenha(passwordEncoder.encode(dto.getSenha()));
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

        // 🔐 Pega usuário logado
        String token = request.getHeader("Authorization");
        Integer idLogado = null;

        if (token != null && token.startsWith("Bearer ")) {
            idLogado = jwtService.extrairIdUsuario(token.substring(7));
        }

        // 🔒 Regra do admin master
        if (id == 1 && (idLogado == null || !idLogado.equals(1))) {
            throw new Exception("Apenas o administrador principal pode editar este usuário.");
        }

        // 🔒 Email duplicado
        usuarioRepository.findByEmail(dto.getEmail())
                .filter(u -> !u.getIdUsuario().equals(id))
                .ifPresent(u -> {
                    throw new RuntimeException("E-mail já cadastrado.");
                });

        // ✅ Agora sim altera
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setTipoPerfil(dto.getTipoPerfil());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        if (dto.getDepartamentoId() != null) {
            DepartamentoEntity depto = departamentoRepository.findById(dto.getDepartamentoId())
                    .orElseThrow(() -> new Exception("Departamento não encontrado."));
            entity.setDepartamento(depto);
        } else {
            entity.setDepartamento(null);
        }

        return convertToDTO(usuarioRepository.save(entity));
    }

    public void alterarStatus(Integer id) throws Exception {
        String token = request.getHeader("Authorization");
        Integer idLogado = null;

        if (token != null && token.startsWith("Bearer ")) {
            idLogado = jwtService.extrairIdUsuario(token.substring(7));
        }

        // 🔒 Não pode desativar a si mesmo
        if (idLogado != null && idLogado.equals(id)) {
            throw new Exception("Você não pode inativar sua própria conta.");
        }

        // 🔒 Admin master nunca pode ser inativado
        if (id == 1) {
            throw new Exception("O usuário administrador principal não pode ser inativado.");
        }

        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));

        entity.setStatus(entity.getStatus() == 1 ? 0 : 1);

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