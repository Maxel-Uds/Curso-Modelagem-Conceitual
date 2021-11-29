package com.maxel.cursomc.repositories;

import com.maxel.cursomc.domain.Categoria;
import com.maxel.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias.cat WHERE obj.name LIKE %:nome% AND cat IN :categorias")
    Page<Produto> search(@Param("nome") String nome, @Param("categorias")List<Categoria> categorias, PageRequest pageRequest);
}
