package com.mvp.produtoapi.controller;

import com.mvp.produtoapi.dto.PedidoDTO;
import com.mvp.produtoapi.entity.Pedido;
import com.mvp.produtoapi.exception.PedidoIllegalArgumentException;
import com.mvp.produtoapi.exception.PedidoInternalServerErrorException;
import com.mvp.produtoapi.exception.PedidoNotFoundException;
import com.mvp.produtoapi.exception.ProdutoNotFoundException;
import com.mvp.produtoapi.service.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/adicionar")
    @Transactional
    public ResponseEntity<PedidoDTO> cadastrarPedido(@RequestBody PedidoDTO pedidoDTO, UriComponentsBuilder uriBuilder) {
        Pedido pedido = pedidoService.cadastrarPedido(pedidoDTO);

        if (pedido == null) {
            throw new PedidoInternalServerErrorException("Erro ao cadastrar pedido");
        }

        URI uri = uriBuilder.path("Pedidos/{id}").buildAndExpand(pedidoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(PedidoDTO.converterToDto(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(@RequestParam int pagina, @RequestParam int quantidade, @RequestParam String ordenacao, @RequestParam String direcao) {
        Page<Pedido> pedidos = pedidoService.listarTodosPedidos(pagina, quantidade, ordenacao, direcao);

        if (pedidos.isEmpty()) {
            throw new ProdutoNotFoundException("Nenhum pedido encontrado");
        }

        List<PedidoDTO> pedidosDTOs = PedidoDTO.converterToList(pedidos.stream().toList());
        return ResponseEntity.ok(pedidosDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable UUID id) {
        Optional<Pedido> pedidos = pedidoService.buscarPedidoPorId(id);

        if (pedidos.isEmpty()) {
            throw new PedidoNotFoundException("Pedido não encontrado");
        }

        return ResponseEntity.ok(PedidoDTO.converterToDto(pedidos.get()));
    }

    @PatchMapping("{id}")
    @Transactional
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable UUID id , @RequestBody PedidoDTO pedidoDTO) {
        Optional<Pedido> pedido = pedidoService.buscarPedidoPorId(id);

        if (pedido.isEmpty()) {
            throw new PedidoNotFoundException("Pedido não encontrado");
        }

        pedidoService.atualizarPedido(pedidoDTO);
        return ResponseEntity.ok(pedidoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> removerPedido(@PathVariable UUID id) {
        Optional<Pedido> Pedido = pedidoService.buscarPedidoPorId(id);

        if (Pedido.isEmpty()) {
            throw new PedidoNotFoundException("Pedido não encontrado");
        }

        pedidoService.removePedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desconto/{desconto}")
    @Transactional
    public ResponseEntity<PedidoDTO> aplicarDesconto(@PathVariable UUID id, @PathVariable BigDecimal desconto) {

        Optional<Pedido> pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido.isEmpty()) {
            throw new PedidoNotFoundException("Pedido não encontrado");
        }
        if (desconto.compareTo(BigDecimal.ZERO) < 0) {
            throw new PedidoIllegalArgumentException("Desconto não pode ser negativo");
        }
        if (desconto.compareTo(new BigDecimal(100)) > 0) {
            throw new PedidoIllegalArgumentException("Desconto não pode ser maior que 100%");
        }

        PedidoDTO pedidoDTO = pedidoService.aplicarDesconto(pedido.get(), desconto);

        return ResponseEntity.ok(pedidoDTO);
    }
}
