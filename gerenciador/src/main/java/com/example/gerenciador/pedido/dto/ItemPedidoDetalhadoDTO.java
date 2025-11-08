package com.example.gerenciador.pedido.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemPedidoDetalhadoDTO {
    private UUID produtoId;
    private String nomeProduto;
    private int quantidade;
    private BigDecimal precoUnitario;
    
    
	public UUID getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(UUID produtoId) {
		this.produtoId = produtoId;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

    
    
}