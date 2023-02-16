package com.mvp.produtoapi.service;

import com.mvp.produtoapi.ENUM.SituacaoProduto;
import com.mvp.produtoapi.ENUM.StatusPedido;
import com.mvp.produtoapi.ENUM.TipoProduto;
import com.mvp.produtoapi.dto.PedidoDTO;
import com.mvp.produtoapi.entity.ItemPedido;
import com.mvp.produtoapi.entity.Pedido;
import com.mvp.produtoapi.repository.PedidoRepository;
import com.mvp.produtoapi.util.UtilAPI;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido cadastrarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoDTO.toEntity();

        for (ItemPedido item : pedidoDTO.getItens()) {
            if (item.getProduto().getSituacao().getNome().equalsIgnoreCase(SituacaoProduto.ATIVO.getNome())) {
                pedido.adicionarItem(item);
            }
            item.setPedido(pedido);
        }

        return pedidoRepository.save(pedido);
    }

    public Page<Pedido> listarTodosPedidos(int pagina, int quantidade, String ordenacao, String direcao) {
        Pageable pageable = PageRequest.of(pagina, quantidade, Sort.by(UtilAPI.obterOrdenacao(direcao), ordenacao));
        return pedidoRepository.findAll(pageable);
    }

    public Pedido buscarPedidoPorId(@PathVariable UUID id) {

        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido atualizarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = buscarPedidoPorId(pedidoDTO.getId());
        pedido.setValorTotal(pedidoDTO.getValorTotal());
        pedido.setData(pedidoDTO.getData());
        pedido.setItens(pedidoDTO.toEntity().getItens());
        pedidoRepository.save(pedido);
        return pedido;
    }
    public void removePedido(UUID id) {
        pedidoRepository.deleteById(id);
    }

    public PedidoDTO aplicarDesconto(UUID id, BigDecimal desconto) {
        Pedido pedido = buscarPedidoPorId(id);

        if (desconto.compareTo(BigDecimal.ZERO) < 0) {
            return PedidoDTO.converterToDto(pedido);
        }

        BigDecimal valorDesconto = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto().getTipoProduto().getId() == TipoProduto.PRODUTO.getId() && pedido.getStatus().getId() == StatusPedido.ABERTO.getId()) {
                BigDecimal novoValorTotal = item.getValorUnitario().multiply(new BigDecimal(item.getQuantidade()));
                valorDesconto = item.getValorTotal().multiply(desconto).divide(new BigDecimal(100));
            }
        }
        pedido.setValorTotal(pedido.getValorTotal().subtract(valorDesconto));
        pedido.setDesconto(desconto);
        pedidoRepository.save(pedido);
        return PedidoDTO.converterToDto(pedido);
    }
}
