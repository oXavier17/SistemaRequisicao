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
    private MaterialRepository materialRepository;

    public List<EstoqueCriticoDTO> listarCriticos() {
        return materialRepository.buscarMateriaisEmEstoqueCritico()
                .stream()
                .map(m -> {
                    EstoqueCriticoDTO dto = new EstoqueCriticoDTO();
                    dto.setMaterialId(m.getIdMaterial());
                    dto.setMaterialNome(m.getNome());
                    dto.setCategoriaNome(
                        m.getCategoria() != null ? m.getCategoria().getNome() : null
                    );
                    dto.setEstoqueAtual(m.getEstoqueAtual());
                    dto.setEstoqueMin(m.getEstoqueMin());
                    dto.setFalta(m.getEstoqueMin() - m.getEstoqueAtual());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}