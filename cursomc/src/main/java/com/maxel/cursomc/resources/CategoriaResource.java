package com.maxel.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    private Integer id;
    private String categoria;

    @RequestMapping(method = RequestMethod.GET)
    public String listar() {
        return "Funcionando";
    }
}
