package com.mvp.produtoapi.repository;

import com.mvp.produtoapi.entity.Pedido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, UUID>, PagingAndSortingRepository<Pedido, UUID> {
}
