package com.daniloalalmeida.estudosjava.controllers;

import com.daniloalalmeida.estudosjava.domain.Pedido;
import com.daniloalalmeida.estudosjava.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Pedido obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }
}
