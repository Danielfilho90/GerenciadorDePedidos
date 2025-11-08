package com.example.gerenciador.pedido.dto;

import java.util.List;

public class PedidoRequestDTO {
    private List<ItemPedidoDTO> itens;

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}

    
}