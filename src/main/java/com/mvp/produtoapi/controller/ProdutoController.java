package com.mvp.produtoapi.controller;

import com.mvp.produtoapi.dto.ProdutoDTO;
import com.mvp.produtoapi.entity.Produto;
import com.mvp.produtoapi.exception.ProdutoBadRequestException;
import com.mvp.produtoapi.exception.ProdutoInternalServerErrorException;
import com.mvp.produtoapi.exception.ProdutoNotFoundException;
import com.mvp.produtoapi.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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
        if (produto == null) {
            throw new ProdutoInternalServerErrorException("Erro ao cadastrar produto");
        }
        URI uri = uriBuilder.path("produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(produtoDTO.converterToDto(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos(@RequestParam int pagina, @RequestParam int quantidade, @RequestParam String ordenacao, @RequestParam String direcao) {
        Page<Produto> produtos = produtoService.listarTodosProdutos(pagina, quantidade, ordenacao, direcao);
        if (produtos.isEmpty()) {
            throw new ProdutoNotFoundException("Nenhum produto encontrado");
        }
        List<ProdutoDTO> produtoDTOs = ProdutoDTO.converterToList(produtos.stream().toList());
        return ResponseEntity.ok(produtoDTOs);
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscarProdutoPorId(@PathVariable UUID id) {
        Optional<Produto> produtos = produtoService.buscarProdutoPorId(id);

        if (produtos.isEmpty()) {
            throw new ProdutoNotFoundException(id);
        }

        return ProdutoDTO.converterToDto(produtos.get());
    }

    @PatchMapping("{id}")
    @Transactional
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable UUID id , @RequestBody ProdutoDTO produtoDTO) {
        Optional<Produto> produto = produtoService.buscarProdutoPorId(id);

        if (produto.isEmpty()) {
            throw new ProdutoNotFoundException(id);
        }

        produtoService.atualizarProduto(produtoDTO);
        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> removerProduto(@PathVariable UUID id) {
        Optional<Produto> produto = produtoService.buscarProdutoPorId(id);

        if (produto.isEmpty()) {
            throw new ProdutoNotFoundException(id);
        }

        Boolean estaEmPedido = produtoService.verificarSeProdutoEstaEmPedido(id);
        if (estaEmPedido) {
            throw new ProdutoBadRequestException(id, "Produto não pode ser removido pois está em um pedido, id: ");
        }

        produtoService.removeProduto(id);
        return ResponseEntity.noContent().build();
    }

}
