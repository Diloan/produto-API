package com.mvp.produtoapi.exception;

import java.util.UUID;

public class PedidoNotFoundException extends RuntimeException{

        public PedidoNotFoundException(UUID id) {
            super("Produto não encontrado com o id: " + id);
        }

        public PedidoNotFoundException(String messagem) {
            super(messagem);
        }
}
