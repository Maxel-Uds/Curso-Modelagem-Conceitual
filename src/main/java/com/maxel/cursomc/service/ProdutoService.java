package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Categoria;
import com.maxel.cursomc.domain.Produto;
import com.maxel.cursomc.repositories.CategoriaRepository;
import com.maxel.cursomc.repositories.ProdutoRepository;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    public Produto findById(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.findDistictByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
