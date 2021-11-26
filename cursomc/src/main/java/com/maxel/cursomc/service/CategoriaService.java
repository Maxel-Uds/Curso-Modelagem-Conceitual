package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Categoria;
import com.maxel.cursomc.dto.CategoriaDTO;
import com.maxel.cursomc.repositories.CategoriaRepository;
import com.maxel.cursomc.service.exceptions.DataIntegrityException;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria findById(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
       return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        findById(obj.getId());
        //O save() serve tanto para inserir quanto para atualizar, depende apenas se o ID é null ou não
        return repo.save(obj);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("A Categoria não pode ser excluída porque possuí produtos associados a ela");
        }
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }
}
