package com.maxel.cursomc.repositories;

import com.maxel.cursomc.domain.ItemPedido;
import com.maxel.cursomc.domain.ItemPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
