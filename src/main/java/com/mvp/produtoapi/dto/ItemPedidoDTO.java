package com.mvp.produtoapi.dto;

import com.mvp.produtoapi.entity.ItemPedido;
import com.mvp.produtoapi.entity.Pedido;
import com.mvp.produtoapi.entity.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemPedidoDTO {

    private UUID id;
    private BigDecimal valorUnitario;
    private int quantidade;
    private Pedido pedido;
    private Produto produto;

    public ItemPedidoDTO() {
    }

    public ItemPedidoDTO(ItemPedido itemPedido) {
        this.quantidade = itemPedido.getQuantidade();
        this.valorUnitario = itemPedido.getProduto().getPreco();
        this.pedido = itemPedido.getPedido();
        this.produto = itemPedido.getProduto();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getValor() {
        return valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public ItemPedido toEntity() {
        return new ItemPedido(quantidade, pedido, produto);
    }

    public static List<ItemPedidoDTO> converterToList(List<ItemPedido> itens) {
        return itens.stream().map(ItemPedidoDTO::new).collect(Collectors.toList());
    }
}
