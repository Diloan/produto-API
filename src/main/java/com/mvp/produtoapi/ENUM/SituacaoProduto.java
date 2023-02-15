package com.mvp.produtoapi.ENUM;

public enum SituacaoProduto {
    ATIVO(0, "Ativo"),
    INATIVO(1, "Inativo");

    private int id;
    private String nome;

    SituacaoProduto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static SituacaoProduto obterSituacao(int id) {
        for (SituacaoProduto situacao : SituacaoProduto.values()) {
            if (situacao.getId() == id) {
                return situacao;
            }
        }
        throw new IllegalArgumentException("Situação de produto inválida: " + id);
    }
}
