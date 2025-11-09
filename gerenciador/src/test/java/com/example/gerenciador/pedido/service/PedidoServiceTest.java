package com.example.gerenciador.pedido.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.gerenciador.auth.model.User;
import com.example.gerenciador.pedido.model.ItemPedido;
import com.example.gerenciador.pedido.model.Pedido;
import com.example.gerenciador.pedido.model.StatusPedido;
import com.example.gerenciador.pedido.repository.PedidoRepository;
import com.example.gerenciador.produto.model.Produto;
import com.example.gerenciador.produto.respository.ProdutoRepository;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockBean
    private PedidoRepository pedidoRepo;

    @MockBean
    private ProdutoRepository produtoRepo;

    @Test
    void deveCriarPedidoComEstoqueSuficiente() {
        User usuario = new User();
        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setPreco(new BigDecimal("50.00"));
        produto.setEstoque(10);

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(2);

        List<ItemPedido> itens = List.of(item);

        when(produtoRepo.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(pedidoRepo.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido pedido = pedidoService.criarPedido(usuario, itens);

        assertEquals(StatusPedido.PENDENTE, pedido.getStatus());
        assertEquals(new BigDecimal("100.00"), pedido.getValorTotal());
        assertEquals(1, pedido.getItens().size());
    }

    @Test
    void deveCancelarPedidoComEstoqueInsuficiente() {
        User usuario = new User();
        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setPreco(new BigDecimal("50.00"));
        produto.setEstoque(1); // insuficiente

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(2);

        List<ItemPedido> itens = List.of(item);

        when(produtoRepo.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(pedidoRepo.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido pedido = pedidoService.criarPedido(usuario, itens);

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
        assertEquals(BigDecimal.ZERO, pedido.getValorTotal());
    }

    @Test
    void devePagarPedidoComEstoqueSuficiente() {
        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setPreco(new BigDecimal("30.00"));
        produto.setEstoque(5);

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(2);

        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setItens(List.of(item));
        pedido.setStatus(StatusPedido.PENDENTE);

        when(pedidoRepo.findById(pedido.getId())).thenReturn(Optional.of(pedido));
        when(produtoRepo.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(pedidoRepo.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));
        when(produtoRepo.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = pedidoService.pagarPedido(pedido.getId());

        assertEquals(StatusPedido.PAGO, resultado.getStatus());
        assertEquals(3, produto.getEstoque()); // estoque reduzido
    }

    @Test
    void deveListarPedidosDoUsuario() {
        User usuario = new User();
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        when(pedidoRepo.findByUsuario(usuario)).thenReturn(List.of(pedido1, pedido2));

        List<Pedido> pedidos = pedidoService.listarPedidosDoUsuario(usuario);

        assertEquals(2, pedidos.size());
    }
}