package com.futbol.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/** CRUD genérico reutilizado por las entidades simples. */
public abstract class CrudController<T> {
    protected final JpaRepository<T, Long> repo;

    protected CrudController(JpaRepository<T, Long> repo) { this.repo = repo; }

    /** Permite limpiar referencias (FK) antes de borrar. */
    protected void antesDeEliminar(Long id) {}

    @GetMapping
    public List<T> listar() { return repo.findAll(); }

    @GetMapping("/{id}")
    public T obtener(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public T crear(@RequestBody T body) { return repo.save(body); }

    @PutMapping("/{id}") @Transactional
    public T actualizar(@PathVariable Long id, @RequestBody T body) {
        T actual = obtener(id);
        BeanUtils.copyProperties(body, actual, "id");
        return repo.save(actual);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional
    public void eliminar(@PathVariable Long id) {
        T actual = obtener(id);
        antesDeEliminar(id);
        repo.delete(actual);
    }
}
