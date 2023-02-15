package com.mvp.produtoapi.controller;

import com.mvp.produtoapi.dto.PedidoDTO;
import com.mvp.produtoapi.entity.Pedido;
import com.mvp.produtoapi.service.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
        PedidoDTO pedidos = pedidoService.cadastrarPedido(pedidoDTO);
        URI uri = uriBuilder.path("Pedidos/{id}").buildAndExpand(pedidoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(pedidos);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodosPedidos(@RequestParam int pagina, @RequestParam int quantidade, @RequestParam String ordenacao, @RequestParam String direcao) {
        Page<Pedido> pedidos = pedidoService.listarTodosPedidos(pagina, quantidade, ordenacao, direcao);
        List<PedidoDTO> pedidosDTOs = PedidoDTO.converterToList(pedidos.stream().toList());
        return ResponseEntity.ok(pedidosDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable UUID id) {
        Pedido pedidos = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(PedidoDTO.converterToDto(pedidos));
    }

    @PatchMapping("{id}")
    @Transactional
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable UUID id , @RequestBody PedidoDTO pedidoDTO) {
        Pedido Pedido = pedidoService.buscarPedidoPorId(id);
        if (Pedido == null) {
            return ResponseEntity.notFound().build();
        }

        pedidoService.atualizarPedido(pedidoDTO);
        return ResponseEntity.ok(pedidoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> removerPedido(@PathVariable UUID id) {
        Pedido Pedido = pedidoService.buscarPedidoPorId(id);
        if (Pedido == null) {
            return ResponseEntity.notFound().build();
        }

        pedidoService.removePedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desconto")
    @Transactional
    public ResponseEntity<PedidoDTO> aplicarDesconto(@PathVariable UUID id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO pedido = pedidoService.aplicarDesconto(id, pedidoDTO.getDesconto());
        return ResponseEntity.ok(pedido);
    }
}
