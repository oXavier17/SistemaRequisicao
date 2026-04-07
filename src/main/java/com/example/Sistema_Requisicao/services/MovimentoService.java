package com.example.Sistema_Requisicao.services;

import com.example.Sistema_Requisicao.dto.*;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentoService {

    @Autowired
    private MovimentoRepository repository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<MovimentoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovimentoDTO registrar(MovimentoCadastroDTO dto) throws Exception {
        // Busca o material
        MaterialEntity material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() -> new Exception("Material não encontrado."));

        // Valida saída — não pode ter estoque negativo
        if (dto.getTipo() == 1 && material.getEstoqueAtual() < dto.getQuantidade()) {
            throw new Exception("Estoque insuficiente. Disponível: "
                + material.getEstoqueAtual() + " " + material.getUnidade());
        }

        // Monta a entidade
        MovimentoEntity entity = new MovimentoEntity();
        entity.setMaterial(material);
        entity.setTipo(dto.getTipo());
        entity.setQuantidade(dto.getQuantidade());
        entity.setDataMovimento(LocalDateTime.now());
        entity.setObservacao(dto.getObservacao());

        // Campos exclusivos da entrada
        if (dto.getTipo() == 0) {
            if (dto.getFornecedorId() == null) {
                throw new Exception("Fornecedor é obrigatório na entrada.");
            }
            if (dto.getPreco() == null || dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Preço é obrigatório na entrada.");
            }
            FornecedorEntity fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new Exception("Fornecedor não encontrado."));
            entity.setFornecedor(fornecedor);
            entity.setPreco(dto.getPreco());

            // Atualiza estoque — soma na entrada
            material.setEstoqueAtual(material.getEstoqueAtual() + dto.getQuantidade());
        } else {
            // Atualiza estoque — subtrai na saída
            material.setEstoqueAtual(material.getEstoqueAtual() - dto.getQuantidade());
        }

        // Salva o movimento e o estoque atualizado
        materialRepository.save(material);
        MovimentoEntity salvo = repository.save(entity);

        return convertToDTO(salvo);
    }

    private MovimentoDTO convertToDTO(MovimentoEntity entity) {
        MovimentoDTO dto = new MovimentoDTO();
        dto.setIdMovimento(entity.getIdMovimento());
        dto.setTipo(entity.getTipo());
        dto.setQuantidade(entity.getQuantidade());
        dto.setDataMovimento(entity.getDataMovimento());
        dto.setPreco(entity.getPreco());
        dto.setObservacao(entity.getObservacao());

        if (entity.getMaterial() != null) {
            dto.setMaterialNome(entity.getMaterial().getNome());
        }
        if (entity.getFornecedor() != null) {
            dto.setFornecedorNome(entity.getFornecedor().getNome());
        }
        if (entity.getRequisicao() != null) {
            dto.setRequisicaoId(entity.getRequisicao().getIdRequisicao());
        }
        return dto;
    }
}