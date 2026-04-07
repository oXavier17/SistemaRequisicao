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
                .map(f -> new FornecedorDTO(f.getIdFornecedor(), f.getNome()))
                .toList();
    }

    public FornecedorDTO salvar(FornecedorDTO dto) throws Exception {
        if (repository.existsByNome(dto.getNome())) {
            throw new Exception("Já existe um fornecedor com este nome.");
        }
        FornecedorEntity entity = new FornecedorEntity();
        entity.setNome(dto.getNome());
        FornecedorEntity salvo = repository.save(entity);
        return new FornecedorDTO(salvo.getIdFornecedor(), salvo.getNome());
    }

    public FornecedorDTO editar(Integer id, FornecedorDTO dto) throws Exception {
        FornecedorEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Fornecedor não encontrado."));
        entity.setNome(dto.getNome());
        FornecedorEntity salvo = repository.save(entity);
        return new FornecedorDTO(salvo.getIdFornecedor(), salvo.getNome());
    }

    public void excluir(Integer id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Fornecedor não encontrado.");
        }
        repository.deleteById(id);
    }
}