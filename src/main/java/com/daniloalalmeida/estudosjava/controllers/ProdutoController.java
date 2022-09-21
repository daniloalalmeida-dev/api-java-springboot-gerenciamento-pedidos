package com.daniloalalmeida.estudosjava.controllers;

import com.daniloalalmeida.estudosjava.controllers.utils.URL;
import com.daniloalalmeida.estudosjava.domain.Categoria;
import com.daniloalalmeida.estudosjava.domain.Pedido;
import com.daniloalalmeida.estudosjava.domain.Produto;
import com.daniloalalmeida.estudosjava.dto.CategoriaDTO;
import com.daniloalalmeida.estudosjava.dto.ProdutoDTO;
import com.daniloalalmeida.estudosjava.services.PedidoService;
import com.daniloalalmeida.estudosjava.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping()
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = service.findDistinctByNomeContainingAndCategoriasIn(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }
}
