package com.daniloalalmeida.estudosjava.repositories;

import com.daniloalalmeida.estudosjava.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
