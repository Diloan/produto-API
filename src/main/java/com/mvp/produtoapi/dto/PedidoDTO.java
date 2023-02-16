package com.mvp.produtoapi.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvp.produtoapi.ENUM.StatusPedido;
import com.mvp.produtoapi.entity.ItemPedido;
import com.mvp.produtoapi.entity.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PedidoDTO {

    private UUID id;
    @JsonProperty("valorTotalPedido")
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDate data = LocalDate.now();
    private StatusPedido status;

    private BigDecimal desconto = BigDecimal.ZERO;
    private List<ItemPedido> itens = new ArrayList<>();

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.valorTotal = pedido.getValorTotal();
        this.data = pedido.getData();
        this.status = pedido.getStatus();
        this.desconto = pedido.getDesconto();
        this.itens = pedido.getItens();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Pedido toEntity() {
        return new Pedido(valorTotal, status, desconto, data, itens);
    }
    public static PedidoDTO converterToDto(Pedido pedido) {
        return new PedidoDTO(pedido);
    }

    public static List<PedidoDTO> converterToList(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoDTO::new).collect(Collectors.toList());
    }
}
