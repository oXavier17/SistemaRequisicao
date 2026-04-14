package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import com.example.Sistema_Requisicao.dto.*;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<MaterialDTO> listarTodos() {
        return materialRepository.findByStatus(1)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaterialDTO> listarTodosComInativos() {
        return materialRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MaterialDTO buscarPorId(Integer id) throws Exception {
        return convertToDTO(
            materialRepository.findById(id)
                .orElseThrow(() -> new Exception("Material não encontrado: " + id))
        );
    }

    public MaterialDTO salvar(MaterialDTO dto) throws Exception {
        MaterialEntity entity = new MaterialEntity();
        entity.setNome(dto.getNome());
        entity.setEstoqueMin(dto.getEstoqueMin());
        entity.setEstoqueAtual(0);
        entity.setStatus(1);
        entity.setUnidade(dto.getUnidade());

        CategoriaEntity categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new Exception("Categoria não encontrada."));
        entity.setCategoria(categoria);

        MaterialEntity salvo = materialRepository.save(entity);
        
        // ← O problema pode ser aqui — após o save, o objeto salvo
        // pode não ter o relacionamento carregado
        // Force o recarregamento pelo ID:
        MaterialEntity recarregado = materialRepository.findById(salvo.getIdMaterial())
                .orElseThrow(() -> new Exception("Erro ao recarregar material."));
        
        return convertToDTO(recarregado);
    }

    public MaterialDTO editar(Integer id, MaterialDTO dto) throws Exception {
        MaterialEntity entity = materialRepository.findById(id)
                .orElseThrow(() -> new Exception("Material não encontrado: " + id));

        entity.setNome(dto.getNome());
        entity.setEstoqueMin(dto.getEstoqueMin());
        entity.setUnidade(dto.getUnidade());
        // estoqueAtual NÃO atualiza aqui — só via MovimentoEstoque

        if (dto.getCategoriaId() != null) {
            CategoriaEntity categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new Exception("Categoria não encontrada."));
            entity.setCategoria(categoria);
        }

        return convertToDTO(materialRepository.save(entity));
    }

    public void alterarStatus(Integer id) throws Exception {
        MaterialEntity entity = materialRepository.findById(id)
                .orElseThrow(() -> new Exception("Material não encontrado: " + id));
        entity.setStatus(entity.getStatus() == 1 ? 0 : 1);
        materialRepository.save(entity);
    }

    private MaterialDTO convertToDTO(MaterialEntity entity) {
        MaterialDTO dto = new MaterialDTO();
        dto.setIdMaterial(entity.getIdMaterial());
        dto.setNome(entity.getNome());
        dto.setEstoqueAtual(entity.getEstoqueAtual());
        dto.setEstoqueMin(entity.getEstoqueMin());
        dto.setStatus(entity.getStatus());
        dto.setUnidade(entity.getUnidade());

        // Verificação segura do relacionamento
        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getIdCategoria());
            dto.setCategoriaNome(entity.getCategoria().getNome());
        } else {
            dto.setCategoriaId(null);
            dto.setCategoriaNome(""); // ← string vazia ao invés de null
        }
        
        return dto;
    }
}