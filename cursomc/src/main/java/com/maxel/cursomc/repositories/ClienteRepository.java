package com.maxel.cursomc.repositories;

import com.maxel.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional(readOnly = true) //Indica que não vai envolver uma operação de banco de dados
    Cliente findByEmail(String email);
}
