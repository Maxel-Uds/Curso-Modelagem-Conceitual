package com.maxel.cursomc.repositories;

import com.maxel.cursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
