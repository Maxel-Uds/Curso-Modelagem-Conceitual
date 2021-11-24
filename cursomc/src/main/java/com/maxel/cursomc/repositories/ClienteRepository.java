package com.maxel.cursomc.repositories;

import com.maxel.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
