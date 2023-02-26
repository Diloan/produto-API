package com.mvp.produtoapi.service;

import com.mvp.produtoapi.dto.PedidoDTO;
import com.mvp.produtoapi.entity.Pedido;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface PedidoService {
    Pedido cadastrarPedido(PedidoDTO pedidoDTO);
    Page<Pedido> listarTodosPedidos(int pagina, int quantidade, String ordenacao, String direcao);
    Optional<Pedido> buscarPedidoPorId(UUID id);
    Pedido atualizarPedido(PedidoDTO pedidoDTO);
    void removerPedido(UUID id);
    PedidoDTO aplicarDesconto(Pedido pedido, BigDecimal desconto);


}
