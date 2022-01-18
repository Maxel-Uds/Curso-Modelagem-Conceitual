package com.maxel.cursomc.resources;

import com.maxel.cursomc.domain.Pedido;
import com.maxel.cursomc.dto.PedidoDTO;
import com.maxel.cursomc.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    PedidoService pedidoService;

    @ApiOperation(value="Busca pelo id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Integer id) {
        Pedido obj = pedidoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation(value="Cadastra um pedido")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido obj = pedidoService.insert(pedidoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return  ResponseEntity.created(uri).build();
    }

    @ApiOperation(value="Cancela um pedido pelo id")
    @RequestMapping(value = "/cancel-order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        pedidoService.cancel(id);
        return  ResponseEntity.noContent().build();
    }

    @ApiOperation(value="Busca todos os pedidos do cliente logado")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "lines", defaultValue = "24") Integer linesPerPage, @RequestParam(value = "order", defaultValue = "instante") String orderBy, @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Pedido> list = pedidoService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
