package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cidade;
import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.Endereco;
import com.maxel.cursomc.dto.EnderecoDTO;
import com.maxel.cursomc.repositories.EnderecoRepository;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CidadeService cidadeService;

    public Endereco findById(Integer id) {
        Optional<Endereco> endereco =enderecoRepository.findById(id);
        return endereco.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    public void update(EnderecoDTO endereco) {
        Cidade cidade = cidadeService.findById(endereco.getCidadeId());
        Cliente cliente = clienteService.findByEmail(endereco.getClienteEmail());
        Endereco end = new Endereco(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCep(),
                cliente,
                cidade
        );

        enderecoRepository.save(end);
    }

    public void delete(Integer id) {
        Endereco endereco = findById(id);
        endereco.setCliente(null);

        enderecoRepository.save(endereco);
    }

    public void create(EnderecoDTO endereco) {
        Cidade cidade = cidadeService.findById(endereco.getCidadeId());
        Cliente cliente = clienteService.findByEmail(endereco.getClienteEmail());
        Endereco end = new Endereco(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCep(),
                cliente,
                cidade
        );

        enderecoRepository.save(end);
    }
}
