package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import com.example.Sistema_Requisicao.dto.*;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository repository;

    public List<DepartamentoDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public DepartamentoDTO salvar(DepartamentoDTO dto) throws Exception {
        if (repository.existsByNome(dto.getNome())) {
            throw new Exception("Já existe um departamento com este nome.");
        }
        DepartamentoEntity entity = new DepartamentoEntity();
        entity.setNome(dto.getNome());
        entity.setStatus(1);
        DepartamentoEntity salvo = repository.save(entity);
        return convertToDTO(salvo);
    }

    public DepartamentoDTO editar(Integer id, DepartamentoDTO dto) throws Exception {
        DepartamentoEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Departamento não encontrado."));
        entity.setNome(dto.getNome());
        return convertToDTO(repository.save(entity));
    }

    public void alterarStatus(Integer id) throws Exception {
        DepartamentoEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Departameno não encontrado."));
        
        // Se for 1, vira 0. Se for 0 (ou qualquer outra coisa), vira 1.
        int novoStatus = (entity.getStatus() == 1) ? 0 : 1;
        
        entity.setStatus(novoStatus);
        repository.save(entity);
    }

    private DepartamentoDTO convertToDTO(DepartamentoEntity entity) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setIdDepartamento(entity.getIdDepartamento());
        dto.setNome(entity.getNome());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}