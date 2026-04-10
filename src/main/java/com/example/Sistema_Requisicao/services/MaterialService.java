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
        return materialRepository.findAll().stream()
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
        entity.setEstoqueAtual(0); // sempre começa zerado
        entity.setStatus(1);
        entity.setUnidade(dto.getUnidade()); // ← enum: "UN", "CX", etc.

        CategoriaEntity categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new Exception("Categoria não encontrada."));
        entity.setCategoria(categoria);

        return convertToDTO(materialRepository.save(entity));
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
        
        // Se for 1, vira 0. Se for 0 (ou qualquer outra coisa), vira 1.
        int novoStatus = (entity.getStatus() == 1) ? 0 : 1;
        
        entity.setStatus(novoStatus);
        materialRepository.save(entity);
    }

    private MaterialDTO convertToDTO(MaterialEntity entity) {
        MaterialDTO dto = new MaterialDTO();
        dto.setIdMaterial(entity.getIdMaterial());   // ← era getId()
        dto.setNome(entity.getNome());
        dto.setEstoqueAtual(entity.getEstoqueAtual());
        dto.setEstoqueMin(entity.getEstoqueMin());
        dto.setStatus(entity.getStatus());
        dto.setUnidade(entity.getUnidade());

        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getIdCategoria());
            dto.setCategoriaNome(entity.getCategoria().getNome());
        }
        return dto;
    }
}