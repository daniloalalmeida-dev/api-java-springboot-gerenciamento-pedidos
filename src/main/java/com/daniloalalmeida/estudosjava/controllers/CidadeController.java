package com.daniloalalmeida.estudosjava.controllers;

import com.daniloalalmeida.estudosjava.domain.Cidade;
import com.daniloalalmeida.estudosjava.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Cidade obj = service.find(id);


        return ResponseEntity.ok().body(obj);
    }
}
