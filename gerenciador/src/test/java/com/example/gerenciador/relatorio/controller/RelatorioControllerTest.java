package com.example.gerenciador.relatorio.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RelatorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbc;

    @Test
    void deveRetornarTopClientes() throws Exception {
        List<Map<String, Object>> resultado = List.of(
            Map.of("username", "cliente1", "total_pedidos", 5),
            Map.of("username", "cliente2", "total_pedidos", 3)
        );

        Mockito.when(jdbc.queryForList(Mockito.anyString())).thenReturn(resultado);

        mockMvc.perform(get("/relatorios/top-clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username").value("cliente1"))
            .andExpect(jsonPath("$[0].total_pedidos").value(5));
    }

    @Test
    void deveRetornarTicketMedio() throws Exception {
        List<Map<String, Object>> resultado = List.of(
            Map.of("username", "cliente1", "ticket_medio", 250.00)
        );

        Mockito.when(jdbc.queryForList(Mockito.anyString())).thenReturn(resultado);

        mockMvc.perform(get("/relatorios/ticket-medio"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].ticket_medio").value(250.00));
    }

    @Test
    void deveRetornarFaturamentoMensal() throws Exception {
        Map<String, Object> resultado = Map.of("faturamento", 10000.00);

        Mockito.when(jdbc.queryForMap(Mockito.anyString())).thenReturn(resultado);

        mockMvc.perform(get("/relatorios/faturamento-mensal"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.faturamento").value(10000.00));
    }
}