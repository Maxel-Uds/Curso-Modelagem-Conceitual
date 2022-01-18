package com.maxel.cursomc.resources;

import com.maxel.cursomc.domain.Cidade;
import com.maxel.cursomc.domain.Estado;
import com.maxel.cursomc.dto.CidadeDTO;
import com.maxel.cursomc.dto.EstadoDTO;
import com.maxel.cursomc.service.CidadeService;
import com.maxel.cursomc.service.EstadoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;
    @Autowired
    private CidadeService cidadeService;

    @ApiOperation(value="Busca todos os estados")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> listDto = list.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @ApiOperation(value="Busca todas as cidades pelo id do estado")
    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
