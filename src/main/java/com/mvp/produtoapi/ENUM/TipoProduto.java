package com.mvp.produtoapi.ENUM;

public enum TipoProduto {
    PRODUTO(0, "Produto"),
    SERVICO(2, "Servico");

    private int id;
    private String nome;

    TipoProduto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static TipoProduto obterTipo(int id) {
        for (TipoProduto tipo : TipoProduto.values()) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de produto inválido: " + id);
    }
}
