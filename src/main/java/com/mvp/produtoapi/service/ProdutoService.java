package com.mvp.produtoapi.service;

import com.mvp.produtoapi.dto.ProdutoDTO;
import com.mvp.produtoapi.entity.Produto;
import com.mvp.produtoapi.repository.ProdutoRepository;
import com.mvp.produtoapi.util.UtilAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(ProdutoDTO produtoDTO) {
       return produtoRepository.save(produtoDTO.toEntity());
    }

    public Page<Produto> listarTodosProdutos(int pagina, int quantidade, String ordenacao, String direcao) {
        Pageable pageable = PageRequest.of(pagina, quantidade, Sort.by(UtilAPI.obterOrdenacao(direcao), ordenacao));
        return produtoRepository.findAll(pageable);
    }

    public Produto buscarProdutoPorId(@PathVariable UUID id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto atualizarProduto(ProdutoDTO produtoDTO) {
        Produto produto = buscarProdutoPorId(produtoDTO.getId());
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setDataCadastro(produtoDTO.getDataCadastro());
        produtoRepository.save(produto);
        return produto;
    }
    public void removeProduto(UUID id) {
        produtoRepository.deleteById(id);
    }

    public Boolean verificarSeProdutoEstaEmPedido(UUID id) {
        return produtoRepository.verificarSeProdutoEstaEmPedido(id);
    }
}
