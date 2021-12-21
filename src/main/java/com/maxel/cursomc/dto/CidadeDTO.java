package com.maxel.cursomc.dto;

import com.maxel.cursomc.domain.Cidade;

import java.io.Serializable;

public class CidadeDTO implements Serializable {

    private Integer id;
    private String nome;

    public CidadeDTO() {}

    public CidadeDTO(Cidade cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
