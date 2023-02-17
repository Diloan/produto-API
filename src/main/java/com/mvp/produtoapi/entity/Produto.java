package com.mvp.produtoapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mvp.produtoapi.ENUM.SituacaoProduto;
import com.mvp.produtoapi.ENUM.TipoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Nome não pode ser vazio")
    @NotNull(message = "Nome é obrigatório")
    private String nome;
    @NotNull(message = "Tipo de produto é obrigatório")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_produto")
    private TipoProduto tipoProduto;
    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;
    @NotNull(message = "Preço não pode ser vazio")
    @NotNull(message = "Preço é obrigatório")
    private BigDecimal preco;
    private LocalDate dataCadastro = LocalDate.now();
    @NotNull(message = "Situação é obrigatório")
    @Enumerated(EnumType.ORDINAL)
    private SituacaoProduto situacao;

    public Produto() {
    }

    public Produto(String nome, TipoProduto tipoProduto, String descricao, BigDecimal preco, SituacaoProduto situacao) {
        this.nome = nome;
        this.tipoProduto = TipoProduto.obterTipo(tipoProduto.getId());
        this.descricao = descricao;
        this.preco = preco;
        this.situacao = SituacaoProduto.obterSituacao(situacao.getId());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public SituacaoProduto getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoProduto situacao) {
        this.situacao = situacao;
    }

}
