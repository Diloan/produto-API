package com.mvp.produtoapi.repository;

import com.mvp.produtoapi.entity.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, UUID>, PagingAndSortingRepository<Produto, UUID> {

    @Query(value = "SELECT CASE WHEN COUNT(ip) > 0 THEN true ELSE false END FROM ItemPedido ip WHERE ip.produto.id = :id")
    Boolean verificarSeProdutoEstaEmPedido(UUID id);

}
 