package com.daniloalalmeida.estudosjava.controllers;

import com.daniloalalmeida.estudosjava.domain.Categoria;
import com.daniloalalmeida.estudosjava.dto.CategoriaDTO;
import com.daniloalalmeida.estudosjava.services.CategoriaService;
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
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll(
    ) {
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDto = list.stream().map((obj) -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDto = list.map((obj) -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Categoria obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
        Categoria obj = service.fromDTO(objDTO);
        obj = service.insert(obj);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
        Categoria obj = service.fromDTO(objDTO);
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
