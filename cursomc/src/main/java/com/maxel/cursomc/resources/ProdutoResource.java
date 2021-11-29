package com.maxel.cursomc.resources;

import com.maxel.cursomc.domain.Produto;
import com.maxel.cursomc.dto.ProdutoDTO;
import com.maxel.cursomc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService produtoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto obj = produtoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome") String nome, @RequestParam(value = "categorias") String categorias, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "lines", defaultValue = "24") Integer linesPerPage, @RequestParam(value = "order", defaultValue = "nome") String orderBy, @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Produto> list = produtoService.search(page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDto);
}
