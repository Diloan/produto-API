package com.mvp.produtoapi.exception;

import java.util.UUID;

public class ProdutoNotFoundException extends RuntimeException{

        public ProdutoNotFoundException(UUID id) {
            super("Produto não encontrado com o id: " + id);
        }

        public ProdutoNotFoundException(String messagem) {
            super(messagem);
        }
}
