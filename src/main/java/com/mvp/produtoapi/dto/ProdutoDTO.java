package com.mvp.produtoapi.dto;

import com.mvp.produtoapi.ENUM.SituacaoProduto;
import com.mvp.produtoapi.ENUM.TipoProduto;
import com.mvp.produtoapi.entity.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProdutoDTO {

    private UUID id;
    private String nome;
    private TipoProduto tipoProduto;
    private String descricao;
    private BigDecimal preco;
    private LocalDate dataCadastro = LocalDate.now();
    private SituacaoProduto situacao = SituacaoProduto.ATIVO;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.tipoProduto = produto.getTipoProduto();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.dataCadastro = produto.getDataCadastro();
        this.situacao = produto.getSituacao();
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

    public Produto toEntity() {
        return new Produto(nome, tipoProduto, descricao, preco, situacao);
    }

    public static ProdutoDTO converterToDto(Produto produto) {
        return new ProdutoDTO(produto);
    }

    public static List<ProdutoDTO> converterToList(List<Produto> produtos) {
        return produtos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
    }

    public SituacaoProduto getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoProduto situacao) {
        this.situacao = situacao;
    }
}
