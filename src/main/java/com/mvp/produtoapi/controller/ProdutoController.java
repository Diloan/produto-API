package com.mvp.produtoapi.controller;

import com.mvp.produtoapi.dto.ProdutoDTO;
import com.mvp.produtoapi.entity.Produto;
import com.mvp.produtoapi.service.ProdutoService;
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
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/adicionar")
    @Transactional
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder uriBuilder) {
        Produto produto = produtoService.cadastrarProduto(produtoDTO);
        URI uri = uriBuilder.path("produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(produtoDTO.converterToDto(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos(@RequestParam int pagina, @RequestParam int quantidade, @RequestParam String ordenacao, @RequestParam String direcao) {
        Page<Produto> produtos = produtoService.listarTodosProdutos(pagina, quantidade, ordenacao, direcao);
        List<ProdutoDTO> produtoDTOs = ProdutoDTO.converterToList(produtos.stream().toList());
        return ResponseEntity.ok(produtoDTOs);
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscarProdutoPorId(@PathVariable UUID id) {
        Produto produtos = produtoService.buscarProdutoPorId(id);
        return ProdutoDTO.converterToDto(produtos);
    }

    @PatchMapping("{id}")
    @Transactional
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable UUID id , @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        produtoService.atualizarProduto(produtoDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> removerProduto(@PathVariable UUID id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        Boolean estaEmPedido = produtoService.verificarSeProdutoEstaEmPedido(id);

        if (estaEmPedido) {
            return ResponseEntity.badRequest().body("Produto não pode ser removido, pois está em um pedido");
        }

        produtoService.removeProduto(id);
        return ResponseEntity.noContent().build();
    }

}
