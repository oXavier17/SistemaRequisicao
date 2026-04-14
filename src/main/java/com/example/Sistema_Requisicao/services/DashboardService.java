package com.example.Sistema_Requisicao.services;

import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Sistema_Requisicao.dto.DashboardDTO;
import com.example.Sistema_Requisicao.dto.EstoqueCriticoDTO;
import com.example.Sistema_Requisicao.dto.RequisicaoDTO;
import com.example.Sistema_Requisicao.entities.RequisicaoEntity;
import com.example.Sistema_Requisicao.repositories.EstoqueCriticoRepository;
import com.example.Sistema_Requisicao.repositories.MaterialRepository;
import com.example.Sistema_Requisicao.repositories.RequisicaoRepository;
import com.example.Sistema_Requisicao.repositories.UsuarioRepository;

@Service
public class DashboardService {

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstoqueCriticoRepository estoqueCriticoRepository;

    public DashboardDTO getResumo() {
        DashboardDTO dto = new DashboardDTO();

        // Total de requisições
        dto.setTotalRequisicoes(requisicaoRepository.count());

        // Requisições pendentes (status = 1 Aberta)
        dto.setRequisicoesPendentes(requisicaoRepository.countByStatus(1));

        // Total de itens em estoque (soma de todos os estoqueAtual ativos)
        dto.setTotalItensEstoque(materialRepository.sumEstoqueAtualAtivos());

        // Usuários ativos
        dto.setTotalUsuariosAtivos(usuarioRepository.countByStatus(1));

        // Estoque crítico — pendentes
        List<EstoqueCriticoDTO> criticos = estoqueCriticoRepository
                .findByStatusAlerta("PENDENTE")
                .stream()
                .map(e -> {
                    EstoqueCriticoDTO c = new EstoqueCriticoDTO();
                    c.setIdAlerta(e.getIdAlerta());
                    c.setMaterialId(e.getMaterial().getIdMaterial());
                    c.setMaterialNome(e.getMaterial().getNome());
                    c.setEstoqueAtual(e.getMaterial().getEstoqueAtual());
                    c.setEstoqueMin(e.getMaterial().getEstoqueMin());
                    c.setFalta(e.getMaterial().getEstoqueMin() - e.getMaterial().getEstoqueAtual());
                    c.setNomeUnidade(e.getMaterial().getUnidade());
                    return c;
                })
                .collect(Collectors.toList());
        dto.setEstoqueCritico(criticos);

        // Requisições por departamento
        dto.setRequisicoesPorDepartamento(
            requisicaoRepository.countByDepartamento()
        );

        // 5 requisições mais recentes
        dto.setRequisicoesMaisRecentes(
            requisicaoRepository.findTop5ByOrderByDataRequisicaoDesc()
                .stream()
                .map(this::convertRequisicao)
                .collect(Collectors.toList())
        );

        return dto;
    }

    private RequisicaoDTO convertRequisicao(RequisicaoEntity entity) {
        RequisicaoDTO dto = new RequisicaoDTO();
        dto.setIdRequisicao(entity.getIdRequisicao());
        dto.setStatus(entity.getStatus());
        dto.setDataRequisicao(entity.getDataRequisicao());
        if (entity.getRequisitante() != null) {
            dto.setNomeRequisitante(entity.getRequisitante().getNome());
        }
        if (entity.getDepartamento() != null) {
            dto.setNomeDepartamento(entity.getDepartamento().getNome());
        }
        return dto;
    }
}