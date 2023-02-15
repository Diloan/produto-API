package com.mvp.produtoapi.util;

import org.springframework.data.domain.Sort;

public class UtilAPI {

    public static Sort.Direction obterOrdenacao(String direcao) {
        if (direcao.equalsIgnoreCase("ASC")) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }
}
