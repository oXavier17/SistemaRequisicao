package com.example.Sistema_Requisicao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.Sistema_Requisicao.entities.*;
import com.example.Sistema_Requisicao.repositories.*;
import com.example.Sistema_Requisicao.dto.*;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    public List<FornecedorDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(f -> new FornecedorDTO(f.getIdFornecedor(), f.getNome(), f.getStatus()))
                .toList();
    }

    public FornecedorDTO salvar(FornecedorDTO dto) throws Exception {
        if (repository.existsByNome(dto.getNome())) {
            throw new Exception("Já existe um fornecedor com este nome.");
        }
        FornecedorEntity entity = new FornecedorEntity();
        entity.setNome(dto.getNome());
        FornecedorEntity salvo = repository.save(entity);
        return new FornecedorDTO(salvo.getIdFornecedor(), salvo.getNome(), salvo.getStatus());
    }

    public FornecedorDTO editar(Integer id, FornecedorDTO dto) throws Exception {
        FornecedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Fornecedor não encontrado."));
        entity.setNome(dto.getNome());
        FornecedorEntity salvo = repository.save(entity);
        return new FornecedorDTO(salvo.getIdFornecedor(), salvo.getNome(), salvo.getStatus());
    }

    public void alterarStatus(Integer id) throws Exception {
        FornecedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Fornecedor não encontrado."));
        
        // Se for 1, vira 0. Se for 0 (ou qualquer outra coisa), vira 1.
        int novoStatus = (entity.getStatus() == 1) ? 0 : 1;
        
        entity.setStatus(novoStatus);
        repository.save(entity);
    }
}