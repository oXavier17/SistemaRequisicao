package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.Sistema_Requisicao.repositories.*;
import com.example.Sistema_Requisicao.dto.*;

@Service
public class EstoqueCriticoService {

    @Autowired
    private EstoqueCriticoRepository repository;

    public List<EstoqueCriticoDTO> listarPendentes() {
        return repository.findByStatusAlerta("PENDENTE").stream()
            .map(entity -> {
                EstoqueCriticoDTO dto = new EstoqueCriticoDTO();
                dto.setIdAlerta(entity.getIdAlerta());
                dto.setMaterialId(entity.getMaterial().getIdMaterial());
                dto.setMaterialNome(entity.getMaterial().getNome());
                dto.setCategoriaNome(entity.getMaterial().getCategoria().getNome());
                dto.setEstoqueAtual(entity.getMaterial().getEstoqueAtual());
                dto.setEstoqueMin(entity.getMaterial().getEstoqueMin());
                dto.setDataGeracao(entity.getDataGeracao());
                dto.setStatusAlerta(entity.getStatusAlerta());
                
                // Cálculo da falta:
                dto.setFalta(entity.getMaterial().getEstoqueMin() - entity.getMaterial().getEstoqueAtual());
                
                return dto;
            })
            .collect(Collectors.toList());
    }
}