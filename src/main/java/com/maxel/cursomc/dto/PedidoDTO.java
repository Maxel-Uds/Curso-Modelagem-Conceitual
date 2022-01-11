package com.maxel.cursomc.dto;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.Endereco;
import com.maxel.cursomc.domain.ItemPedido;
import com.maxel.cursomc.domain.Pagamento;

import java.io.Serializable;
import java.util.List;

public class PedidoDTO implements Serializable {


    private Cliente cliente;

    private Endereco enderecoDeEntrega;

    private Pagamento pagamento;

    private List<ItemPedido> itens;

    public PedidoDTO() {}

    public PedidoDTO(Cliente cliente, Endereco enderecoDeEntrega, Pagamento pagamento, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
        this.pagamento = pagamento;
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
