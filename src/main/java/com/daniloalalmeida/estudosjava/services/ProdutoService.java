package com.daniloalalmeida.estudosjava.services;

import com.daniloalalmeida.estudosjava.domain.Categoria;
import com.daniloalalmeida.estudosjava.domain.Produto;
import com.daniloalalmeida.estudosjava.exceptions.ObjectNotFoundException;
import com.daniloalalmeida.estudosjava.repositories.CategoriaRepository;
import com.daniloalalmeida.estudosjava.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado. Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}