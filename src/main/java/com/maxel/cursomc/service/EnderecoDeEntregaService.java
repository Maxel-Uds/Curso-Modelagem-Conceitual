package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Endereco;
import com.maxel.cursomc.domain.EnderecoDeEntrega;
import com.maxel.cursomc.repositories.EnderecoDeEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoDeEntregaService {

    @Autowired
    private EnderecoDeEntregaRepository enderecoDeEntregaRepository;

    public EnderecoDeEntrega findById(Integer id) {
        Optional<EnderecoDeEntrega> entrega = enderecoDeEntregaRepository.findById(id);
        return entrega.orElse(null);
    }

    public EnderecoDeEntrega insert(EnderecoDeEntrega endereco) {
        return enderecoDeEntregaRepository.save(endereco);
    }

    public EnderecoDeEntrega generateAddress(Endereco endereco) {
        return new EnderecoDeEntrega(endereco);
    }
}
