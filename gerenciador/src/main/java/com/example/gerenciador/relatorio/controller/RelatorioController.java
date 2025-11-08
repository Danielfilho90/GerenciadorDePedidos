package com.example.gerenciador.relatorio.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired private JdbcTemplate jdbc;

    @GetMapping("/top-clientes")
    public List<Map<String, Object>> topClientes() {
        return jdbc.queryForList("""
            SELECT u.username, COUNT(p.id) AS total_pedidos
            FROM pedido p
            JOIN user u ON p.usuario_id = u.id
            WHERE p.status = 'PAGO'
            GROUP BY u.id
            ORDER BY total_pedidos DESC
            LIMIT 5
        """);
    }

    @GetMapping("/ticket-medio")
    public List<Map<String, Object>> ticketMedio() {
        return jdbc.queryForList("""
            SELECT u.username, AVG(p.valor_total) AS ticket_medio
            FROM pedido p
            JOIN user u ON p.usuario_id = u.id
            WHERE p.status = 'PAGO'
            GROUP BY u.id
        """);
    }

    @GetMapping("/faturamento-mensal")
    public Map<String, Object> faturamentoMensal() {
        return jdbc.queryForMap("""
            SELECT SUM(p.valor_total) AS faturamento
            FROM pedido p
            WHERE p.status = 'PAGO'
            AND MONTH(p.criado_em) = MONTH(CURRENT_DATE())
            AND YEAR(p.criado_em) = YEAR(CURRENT_DATE())
        """);
    }
}