package com.maxel.cursomc.resources;

import com.maxel.cursomc.dto.EnderecoDTO;
import com.maxel.cursomc.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoResource {

    @Autowired
    private EnderecoService enderecoService;

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody EnderecoDTO enderecoDTO) {
        enderecoService.update(enderecoDTO);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody EnderecoDTO enderecoDTO) {
        enderecoService.create(enderecoDTO);
        return ResponseEntity.noContent().build();
    }
}
