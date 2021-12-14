package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.*;
import com.maxel.cursomc.domain.enums.EstadoPagamento;
import com.maxel.cursomc.repositories.ItemPedidoRepository;
import com.maxel.cursomc.repositories.PagamentoRepository;
import com.maxel.cursomc.repositories.PedidoRepository;
import com.maxel.cursomc.security.UserSpringSecurity;
import com.maxel.cursomc.service.exceptions.AuthorizationException;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EmailService emailService;

    public Pedido findById(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.findById(obj.getCliente().getId()));
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
            x.setProduto(produtoService.findById(x.getProduto().getId()));
            x.setPreco(x.getProduto().getPreco());
            x.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfitmationHtmlEmail(obj);
        return obj;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        if(loggedUser == null) { throw new AuthorizationException("Acesso negado"); }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.findById(loggedUser.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }
}
