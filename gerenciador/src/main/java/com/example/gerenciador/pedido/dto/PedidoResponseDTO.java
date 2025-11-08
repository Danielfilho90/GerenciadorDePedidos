package com.example.gerenciador.pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.gerenciador.pedido.model.StatusPedido;

public class PedidoResponseDTO {
    private UUID id;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private LocalDateTime criadoEm;
    private List<ItemPedidoDetalhadoDTO> itens;
    
    
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public StatusPedido getStatus() {
		return status;
	}
	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}
	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}
	public List<ItemPedidoDetalhadoDTO> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedidoDetalhadoDTO> itens) {
		this.itens = itens;
	}

    
}