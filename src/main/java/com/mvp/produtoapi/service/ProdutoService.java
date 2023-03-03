package com.mvp.produtoapi.service;

import com.mvp.produtoapi.dto.ProdutoDTO;
import com.mvp.produtoapi.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoService {
    Produto cadastrarProduto(ProdutoDTO produtoDTO);
    Page<Produto> listarTodosProdutos(int pagina, int quantidade, String ordenacao, String direcao);
    Optional<Produto> buscarProdutoPorId(@PathVariable UUID id);
    Produto atualizarProduto(ProdutoDTO produtoDTO);
    void removerProduto(UUID id);
    Boolean verificarSeProdutoEstaEmPedido(UUID id);

}
