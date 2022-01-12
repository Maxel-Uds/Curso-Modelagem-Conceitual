package com.maxel.cursomc.dto;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.Endereco;
import com.maxel.cursomc.domain.ItemPedido;
import com.maxel.cursomc.domain.Pagamento;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PedidoDTO implements Serializable {

    private Cliente cliente;
    private Endereco enderecoDeEntrega;
    private Pagamento pagamento;
    private Set<ItemPedido> itens = new HashSet<ItemPedido>();
    private Integer minutosUntilUtc;

    public PedidoDTO() {}

    public PedidoDTO(Cliente cliente, Endereco enderecoDeEntrega, Pagamento pagamento, Set<ItemPedido> itens, Integer minutosUntilUtc) {
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
        this.pagamento = pagamento;
        this.itens = itens;
        this.minutosUntilUtc = minutosUntilUtc;
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

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public Integer getMinutosUntilUtc() {
        return minutosUntilUtc;
    }

    public void setMinutosUntilUtc(Integer minutosUntilUtc) {
        this.minutosUntilUtc = minutosUntilUtc;
    }
}
