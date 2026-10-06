package com.futbol.controller;

import com.futbol.dto.*;
import com.futbol.model.Jugador;
import com.futbol.service.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clubes")
public class ClubController {
    private final ClubService service;

    public ClubController(ClubService service) { this.service = service; }

    @GetMapping public List<ClubResumen> listar() { return service.listar(); }

    @GetMapping("/{id}") public ClubDetalle obtener(@PathVariable Long id) { return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ClubDetalle crear(@RequestBody ClubRequest r) { return service.crear(r); }

    @PutMapping("/{id}")
    public ClubDetalle actualizar(@PathVariable Long id, @RequestBody ClubRequest r) { return service.actualizar(id, r); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }

    @PostMapping("/{id}/jugadores") @ResponseStatus(HttpStatus.CREATED)
    public ClubDetalle agregarJugador(@PathVariable Long id, @RequestBody Jugador j) { return service.agregarJugador(id, j); }

    @DeleteMapping("/{id}/jugadores/{jugadorId}")
    public ClubDetalle quitarJugador(@PathVariable Long id, @PathVariable Long jugadorId) { return service.quitarJugador(id, jugadorId); }
}
