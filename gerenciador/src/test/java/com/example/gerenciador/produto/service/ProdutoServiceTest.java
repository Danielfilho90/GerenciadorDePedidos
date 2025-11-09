package com.example.gerenciador.produto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.gerenciador.produto.model.Produto;
import com.example.gerenciador.produto.respository.ProdutoRepository;

@SpringBootTest
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setNome("Notebook");
        produto.setDescricao("Notebook Gamer");
        produto.setPreco(new BigDecimal("4500.00"));
        produto.setCategoria("Eletrônicos");
        produto.setEstoque(10);
    }

    @Test
    void deveCriarProduto() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto salvo = produtoService.criar(produto);

        assertNotNull(salvo);
        assertEquals("Notebook", salvo.getNome());
    }

    @Test
    void deveAtualizarProduto() {
        UUID id = produto.getId();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        produto.setPreco(new BigDecimal("4000.00"));
        Produto atualizado = produtoService.atualizar(id, produto);

        assertEquals(new BigDecimal("4000.00"), atualizado.getPreco());
    }

    @Test
    void deveDeletarProduto() {
        UUID id = produto.getId();
        doNothing().when(produtoRepository).deleteById(id);

        produtoService.deletar(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    void deveListarTodosProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(produto));

        List<Produto> produtos = produtoService.listarTodos();

        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
    }

    @Test
    void deveBuscarProdutoPorId() {
        UUID id = produto.getId();
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        Produto encontrado = produtoService.buscarPorId(id);

        assertEquals("Notebook", encontrado.getNome());
    }
}