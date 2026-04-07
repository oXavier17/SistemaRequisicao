package com.example.Sistema_Requisicao.services;

import com.example.Sistema_Requisicao.dto.*;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequisicaoService {

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private ItemRequisicaoRepository itemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<RequisicaoDTO> listarTodas() {
        return requisicaoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequisicaoDTO salvar(RequisicaoDTO dto) throws Exception {
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new Exception("A requisição precisa de pelo menos um item.");
        }

        UsuarioEntity usuario = usuarioRepository.findById(dto.getRequisitanteId())
                .orElseThrow(() -> new Exception("Usuário requisitante não encontrado."));

        RequisicaoEntity requisicao = new RequisicaoEntity();
        requisicao.setDataRequisicao(LocalDateTime.now());
        requisicao.setStatus(1); // Aberta
        requisicao.setObservacao(dto.getObservacao());
        requisicao.setRequisitante(usuario);
        requisicao.setDepartamento(usuario.getDepartamento());

        RequisicaoEntity salva = requisicaoRepository.save(requisicao);

        for (ItemRequisicaoDTO itemDto : dto.getItens()) {
            MaterialEntity material = materialRepository.findById(itemDto.getIdMaterial()) // ← era getMaterialId()
                    .orElseThrow(() -> new Exception("Material não encontrado: " + itemDto.getIdMaterial()));

            ItemRequisicaoEntity item = new ItemRequisicaoEntity();
            item.setRequisicao(salva);
            item.setMaterial(material);
            item.setQuantidade(itemDto.getQuantidade());
            itemRepository.save(item);
        }

        return convertToDTO(requisicaoRepository.findById(salva.getIdRequisicao()).get());
    }

    public RequisicaoDTO atualizarStatus(Integer id, Integer novoStatus) throws Exception {
        if (novoStatus < 1 || novoStatus > 5) {
            throw new Exception("Status inválido.");
        }
        RequisicaoEntity entity = requisicaoRepository.findById(id)
                .orElseThrow(() -> new Exception("Requisição não encontrada."));

        // Regras de transição de status
        // Não pode voltar atrás nem alterar entregue/cancelada
        if (entity.getStatus() == 4 || entity.getStatus() == 5) {
            throw new Exception("Requisição já finalizada, não pode ser alterada.");
        }

        entity.setStatus(novoStatus);

        // Se estiver sendo entregue, registra a data de envio
        if (novoStatus == 4) {
            entity.setDataEnvio(LocalDateTime.now());
        }

        return convertToDTO(requisicaoRepository.save(entity));
    }

    public RequisicaoDTO editarQuantidadeItem(Integer requisicaoId,
                                               Integer materialId,
                                               Integer novaQuantidade) throws Exception {
        if (novaQuantidade <= 0) {
            throw new Exception("Quantidade deve ser maior que zero.");
        }

        RequisicaoEntity requisicao = requisicaoRepository.findById(requisicaoId)
                .orElseThrow(() -> new Exception("Requisição não encontrada."));

        // Só permite editar em requisições abertas ou em separação
        if (requisicao.getStatus() != 1 && requisicao.getStatus() != 2) {
            throw new Exception("Só é possível editar itens de requisições abertas ou em separação.");
        }

        ItemRequisicaoEntity item = itemRepository
                .findByRequisicaoIdAndMaterialId(requisicaoId, materialId)
                .orElseThrow(() -> new Exception("Item não encontrado na requisição."));

        item.setQuantidade(novaQuantidade);
        itemRepository.save(item);

        return convertToDTO(requisicaoRepository.findById(requisicaoId).get());
    }

    private RequisicaoDTO convertToDTO(RequisicaoEntity entity) {
        RequisicaoDTO dto = new RequisicaoDTO();
        dto.setIdRequisicao(entity.getIdRequisicao());
        dto.setDataRequisicao(entity.getDataRequisicao());
        dto.setDataEnvio(entity.getDataEnvio());
        dto.setStatus(entity.getStatus());
        dto.setObservacao(entity.getObservacao());

        if (entity.getRequisitante() != null) {
            dto.setRequisitanteId(entity.getRequisitante().getIdUsuario());
            dto.setNomeRequisitante(entity.getRequisitante().getNome());
        }

        if (entity.getFuncionario() != null) {
            dto.setFuncionarioId(entity.getFuncionario().getIdUsuario());
        }

        if (entity.getDepartamento() != null) {
            dto.setDepartamentoId(entity.getDepartamento().getIdDepartamento());
            dto.setNomeDepartamento(entity.getDepartamento().getNome());
        }

        if (entity.getItens() != null) {
            dto.setItens(entity.getItens().stream().map(item -> {
                ItemRequisicaoDTO iDto = new ItemRequisicaoDTO();
                iDto.setIdItem(item.getIdItem());
                iDto.setIdMaterial(item.getMaterial().getIdMaterial());
                iDto.setNomeMaterial(item.getMaterial().getNome());
                iDto.setQuantidade(item.getQuantidade());
                iDto.setNomeUnidade(item.getMaterial().getUnidade());
                return iDto;
            }).collect(Collectors.toList()));
        }

        return dto;
    }
}