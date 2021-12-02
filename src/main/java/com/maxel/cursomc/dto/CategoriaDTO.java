package com.maxel.cursomc.dto;

import com.maxel.cursomc.domain.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private Integer id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=5, max=80, message = "O tamanho deve ser de 5 a 80 caracteres")
    private String nome;

    public CategoriaDTO() {}

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
