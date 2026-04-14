package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import com.example.Sistema_Requisicao.dto.*;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(cat -> new CategoriaDTO(cat.getIdCategoria(), cat.getNome(), cat.getStatus()))
                .toList();
    }

    public CategoriaDTO salvar(CategoriaDTO dto) throws Exception {
        if (repository.existsByNome(dto.getNome())) {
            throw new Exception("Já existe uma categoria com este nome.");
        }
        CategoriaEntity entity = new CategoriaEntity();
        entity.setNome(dto.getNome());
        CategoriaEntity salvo = repository.save(entity);
        return new CategoriaDTO(salvo.getIdCategoria(), salvo.getNome(), salvo.getStatus());
    }

    public CategoriaDTO editar(Integer id, CategoriaDTO dto) throws Exception {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Categoria não encontrada."));
        if (repository.existsByNomeAndIdCategoriaNot(dto.getNome(), id)) {
            throw new Exception("Já existe uma categoria com este nome.");
        }
        entity.setNome(dto.getNome());
        CategoriaEntity salvo = repository.save(entity);
        return new CategoriaDTO(salvo.getIdCategoria(), salvo.getNome(), salvo.getStatus());
    }

    public void alterarStatus(Integer id) throws Exception {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Categoria não encontrada."));
        entity.setStatus(entity.getStatus() == 1 ? 0 : 1);
        repository.save(entity);
    }
}