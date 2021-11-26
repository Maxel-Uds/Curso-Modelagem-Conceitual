package com.maxel.cursomc.dto;

import com.maxel.cursomc.domain.Categoria;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private Integer id;
    private String nome;

    public CategoriaDTO() {}

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return nome;
    }
}
