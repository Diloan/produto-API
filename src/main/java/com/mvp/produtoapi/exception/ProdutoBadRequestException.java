package com.mvp.produtoapi.exception;

import java.util.UUID;

public class ProdutoBadRequestException extends RuntimeException{

        public ProdutoBadRequestException(UUID id, String message) {
            super( message + id);
        }
}
