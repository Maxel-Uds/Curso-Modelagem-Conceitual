package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.ItemPedido;
import com.maxel.cursomc.domain.PagamentoComBoleto;
import com.maxel.cursomc.domain.Pedido;
import com.maxel.cursomc.domain.enums.EstadoPagamento;
import com.maxel.cursomc.repositories.ItemPedidoRepository;
import com.maxel.cursomc.repositories.PagamentoRepository;
import com.maxel.cursomc.repositories.PedidoRepository;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido findById(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            BoletoService.preencherPagamentocomBoleto(pagto, obj.getInstante());
        }
        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido x : obj.getItens()) {
            x.setDesconto(0.00);
            x.setPreco(produtoService.findById(x.getProduto().getId()).getPreco());
            x.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }
}
