package com.mvp.produtoapi.exception;

import java.util.UUID;

public class PedidoBadRequestException extends RuntimeException{

        public PedidoBadRequestException(UUID id, String message) {
            super( message + id);
        }
}
