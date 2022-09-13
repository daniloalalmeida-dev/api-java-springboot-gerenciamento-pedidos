package com.daniloalalmeida.estudosjava.services;

import com.daniloalalmeida.estudosjava.domain.Cidade;
import com.daniloalalmeida.estudosjava.exceptions.ObjectNotFoundException;
import com.daniloalalmeida.estudosjava.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repo;

    public Cidade find(Integer id) {
        Optional<Cidade> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id: " + id
        ));
    }
}
