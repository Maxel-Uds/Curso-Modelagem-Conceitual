package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.*;
import com.maxel.cursomc.domain.enums.EstadoPagamento;
import com.maxel.cursomc.domain.enums.TipoCliente;
import com.maxel.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instantiateTestDatabase() throws Exception {
        Categoria cat1 = new Categoria(null, "Escritório");
        Categoria cat2 = new Categoria(null, "Informática");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto pro1 = new Produto(null, "Computador", 2000.00);
        Produto pro2 = new Produto(null, "Impressora", 800.00);
        Produto pro3 = new Produto(null, "Mouse", 80.00);
        Produto pro4 = new Produto(null, "Mesa de escritório", 300.00);
        Produto pro5 = new Produto(null, "Toalha", 50.00);
        Produto pro6 = new Produto(null, "Colcha", 200.00);
        Produto pro7 = new Produto(null, "TV true color", 1200.00);
        Produto pro8 = new Produto(null, "Roçadeira", 800.00);
        Produto pro9 = new Produto(null, "Abajour", 100.00);
        Produto pro10 = new Produto(null, "Pendente", 180.00);
        Produto pro11 = new Produto(null, "Shampoo", 90.00);

        cat1.getProdutos().addAll(Arrays.asList(pro2, pro4));
        cat2.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3));
        cat3.getProdutos().addAll(Arrays.asList(pro5, pro6));
        cat4.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3, pro7));
        cat5.getProdutos().addAll(Arrays.asList(pro8));
        cat6.getProdutos().addAll(Arrays.asList(pro9, pro10));
        cat7.getProdutos().addAll(Arrays.asList(pro11));

        pro1.getCategorias().addAll(Arrays.asList(cat2, cat4));
        pro2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        pro3.getCategorias().addAll(Arrays.asList(cat2, cat4));
        pro4.getCategorias().addAll(Arrays.asList(cat1));
        pro5.getCategorias().addAll(Arrays.asList(cat3));
        pro6.getCategorias().addAll(Arrays.asList(cat3));
        pro7.getCategorias().addAll(Arrays.asList(cat4));
        pro8.getCategorias().addAll(Arrays.asList(cat5));
        pro9.getCategorias().addAll(Arrays.asList(cat6));
        pro10.getCategorias().addAll(Arrays.asList(cat6));
        pro11.getCategorias().addAll(Arrays.asList(cat7));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(pro1, pro2, pro3, pro4, pro5, pro6, pro7, pro8, pro9, pro10, pro11));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade cid1 = new Cidade(null, "Uberlândia", est1);
        Cidade cid2 = new Cidade(null, "São Paulo", est2);
        Cidade cid3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(cid1));
        est2.getCidades().addAll(Arrays.asList(cid2, cid3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maxellopes32@gmail.com", "363789122377", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("98105500"));

        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "3822034", cli1, cid1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2021 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/11/2021 19:27"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1,6);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/11/2021 00:00"), null);

        ped1.setPagamento(pagto1);
        ped2.setPagamento(pagto2);
        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, pro1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, pro3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, pro2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        pro1.getItens().addAll(Arrays.asList(ip1));
        pro2.getItens().addAll(Arrays.asList(ip3));
        pro3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
