package com.example.gerenciador.pedido.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gerenciador.auth.model.User;
import com.example.gerenciador.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByUsuario(User usuario);
}