package com.mvp.produtoapi.service;

import com.mvp.produtoapi.ENUM.SituacaoProduto;
import com.mvp.produtoapi.ENUM.StatusPedido;
import com.mvp.produtoapi.ENUM.TipoProduto;
import com.mvp.produtoapi.dto.PedidoDTO;
import com.mvp.produtoapi.entity.ItemPedido;
import com.mvp.produtoapi.entity.Pedido;
import com.mvp.produtoapi.exception.PedidoInternalServerErrorException;
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
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PedidoServiceImp implements PedidoService{
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoServiceImp(PedidoRepository pedidoRepository) {
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

    public Optional<Pedido> buscarPedidoPorId(@PathVariable UUID id) {

        return pedidoRepository.findById(id);
    }

    public Pedido atualizarPedido(PedidoDTO pedidoDTO) {
        Optional<Pedido> pedido = buscarPedidoPorId(pedidoDTO.getId());
        if(pedido.isPresent()) {
            pedido.get().setValorTotal(pedidoDTO.getValorTotal());
            pedido.get().setData(pedidoDTO.getData());
            pedido.get().setItens(pedidoDTO.toEntity().getItens());
            pedido.get().setStatus(pedidoDTO.getStatus());
            for (ItemPedido item : pedidoDTO.getItens()) {
                item.setPedido(pedido.get());
            }
            pedidoRepository.save(pedido.get());
            return pedido.get();
        } else {
            throw new PedidoInternalServerErrorException("Erro ao atualizar pedido");
        }

    }
    public void removerPedido(UUID id) {
        pedidoRepository.deleteById(id);
    }

    public PedidoDTO aplicarDesconto(Pedido pedido, BigDecimal desconto) {

        BigDecimal valorDesconto = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto().getTipoProduto().getId() == TipoProduto.PRODUTO.getId() && pedido.getStatus().getId() == StatusPedido.ABERTO.getId()) {
                valorDesconto = item.getValorTotal().multiply(desconto).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            }
        }
        pedido.setValorTotal(pedido.getValorTotal().subtract(valorDesconto));
        pedido.setDesconto(desconto);

        pedidoRepository.save(pedido);

        return PedidoDTO.converterToDto(pedido);
    }
}
