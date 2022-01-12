package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.*;
import com.maxel.cursomc.domain.enums.EstadoPagamento;
import com.maxel.cursomc.dto.PedidoDTO;
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
    @Autowired
    private EnderecoDeEntregaService entregaService;
    @Autowired
    private EnderecoService enderecoService;

    public Pedido findById(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    @Transactional
    public Pedido insert(PedidoDTO pedidoDTO) {
        Cliente client = clienteService.findById(pedidoDTO.getCliente().getId());
        EnderecoDeEntrega entrega = addAddress(pedidoDTO);
        Pedido pedido = new Pedido(null, (new Date()), client, entrega);

        entrega.setPedido(pedido);

        pedido.setPagamento(pedidoDTO.getPagamento());
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if(pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            BoletoService.preencherPagamentocomBoleto(pagto, pedido.getInstante());
        }

        entregaService.insert(entrega);
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for(ItemPedido item : pedido.getItens()) {
            item.setDesconto(0.00);
            item.setProduto(produtoService.findById(item.getProduto().getId()));
            item.setPreco(item.getProduto().getPreco());
            item.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfitmationHtmlEmail(pedido);
        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        if(loggedUser == null) { throw new AuthorizationException("Acesso negado"); }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.findById(loggedUser.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }

    private EnderecoDeEntrega addAddress(PedidoDTO ped) {
        var enderecoDb = enderecoService.findById(ped.getEnderecoDeEntrega().getId());
        return entregaService.generateAddress(enderecoDb);
    }
}
