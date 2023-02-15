package com.mvp.produtoapi.ENUM;

public enum StatusPedido {
    ABERTO(0, "Aberto"),
    FECHADO(1, "Fechado"),
    CANCELADO(2, "Cancelado");

    private int id;
    private String nome;

    StatusPedido(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static StatusPedido obterStatus(int id) {
        for (StatusPedido status : StatusPedido.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de pedido inválido: " + id);
    }
}
