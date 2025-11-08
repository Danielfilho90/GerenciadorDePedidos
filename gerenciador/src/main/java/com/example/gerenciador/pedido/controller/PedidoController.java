package com.example.gerenciador.pedido.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciador.auth.service.UserDetailsImpl;
import com.example.gerenciador.pedido.model.ItemPedido;
import com.example.gerenciador.pedido.model.Pedido;
import com.example.gerenciador.pedido.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired private PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody List<ItemPedido> itens, @AuthenticationPrincipal UserDetailsImpl usuario) {
        return ResponseEntity.ok(service.criarPedido(usuario.getUser(), itens));
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<Pedido> pagar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.pagarPedido(id));
    }

    @GetMapping("/meus")
    public ResponseEntity<List<Pedido>> listarMeusPedidos(@AuthenticationPrincipal UserDetailsImpl usuario) {
        return ResponseEntity.ok(service.listarPedidosDoUsuario(usuario.getUser()));
    }
}