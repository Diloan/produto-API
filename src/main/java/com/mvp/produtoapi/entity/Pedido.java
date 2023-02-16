package com.mvp.produtoapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mvp.produtoapi.ENUM.StatusPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "valor_total")
    @NotBlank(message = "Valor total não pode ser vazio")
    @NotNull(message = "Valor total é obrigatório")
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDate data = LocalDate.now();
    @NotBlank(message = "Status não pode ser vazio")
    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;
    @Max(value = 100, message = "Desconto deve ser menor ou igual a 100%")
    private BigDecimal desconto = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(BigDecimal valorTotal, StatusPedido status, BigDecimal desconto, LocalDate data, List<ItemPedido> itens) {
        this.valorTotal = valorTotal;
        this.data = data;
        this.status = StatusPedido.obterStatus(status.getId());
        this.desconto = desconto;
        this.itens = itens;
    }

    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        item.setValorUnitario(item.getProduto().getPreco());
        this.valorTotal = this.valorTotal.add(item.getValorTotal());
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

}
