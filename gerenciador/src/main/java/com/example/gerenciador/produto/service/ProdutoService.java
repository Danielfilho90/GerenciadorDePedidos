package com.example.gerenciador.produto.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gerenciador.produto.model.Produto;
import com.example.gerenciador.produto.respository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto criar(Produto p) {
        p.setId(UUID.randomUUID());
        return repository.save(p);
    }

    public Produto atualizar(UUID id, Produto p) {
        Produto existente = repository.findById(id).orElseThrow();
        existente.setNome(p.getNome());
        existente.setDescricao(p.getDescricao());
        existente.setPreco(p.getPreco());
        existente.setCategoria(p.getCategoria());
        existente.setEstoque(p.getEstoque());
        return repository.save(existente);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}