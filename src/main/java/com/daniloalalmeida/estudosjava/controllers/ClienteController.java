package com.daniloalalmeida.estudosjava.controllers;

import com.daniloalalmeida.estudosjava.domain.Cliente;
import com.daniloalalmeida.estudosjava.dto.ClienteDTO;
import com.daniloalalmeida.estudosjava.dto.ClienteNewDTO;
import com.daniloalalmeida.estudosjava.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(
    ) {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDto = list.stream().map((obj) -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDto = list.map((obj) -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) {
        Cliente obj = service.fromDTO(objDTO);
        obj = service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
        Cliente obj = service.fromDTO(objDTO);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
