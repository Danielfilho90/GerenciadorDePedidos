package com.example.gerenciador.pedido.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gerenciador.auth.model.User;
import com.example.gerenciador.pedido.model.ItemPedido;
import com.example.gerenciador.pedido.model.Pedido;
import com.example.gerenciador.pedido.model.StatusPedido;
import com.example.gerenciador.pedido.repository.PedidoRepository;
import com.example.gerenciador.produto.model.Produto;
import com.example.gerenciador.produto.respository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepo;
    @Autowired private ProdutoRepository produtoRepo;

    public Pedido criarPedido(User usuario, List<ItemPedido> itens) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setItens(new ArrayList<>());
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedido item : itens) {
            Produto produto = produtoRepo.findById(item.getProduto().getId()).orElseThrow();
            if (produto.getEstoque() < item.getQuantidade()) {
                pedido.setStatus(StatusPedido.CANCELADO);
                pedido.setValorTotal(BigDecimal.ZERO);
                return pedidoRepo.save(pedido);
            }
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);
            pedido.getItens().add(item);
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        pedido.setValorTotal(total);
        return pedidoRepo.save(pedido);
    }

    public Pedido pagarPedido(UUID id) {
        Pedido pedido = pedidoRepo.findById(id).orElseThrow();
        if (pedido.getStatus() != StatusPedido.PENDENTE) return pedido;

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepo.findById(item.getProduto().getId()).orElseThrow();
            if (produto.getEstoque() < item.getQuantidade()) {
                pedido.setStatus(StatusPedido.CANCELADO);
                return pedidoRepo.save(pedido);
            }
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produtoRepo.save(produto);
        }

        pedido.setStatus(StatusPedido.PAGO);
        return pedidoRepo.save(pedido);
    }

    public List<Pedido> listarPedidosDoUsuario(User usuario) {
        return pedidoRepo.findByUsuario(usuario);
    }
}