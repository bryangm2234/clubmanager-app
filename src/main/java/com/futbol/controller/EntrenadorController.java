package com.futbol.controller;

import com.futbol.model.Entrenador;
import com.futbol.repository.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/entrenadores")
public class EntrenadorController extends CrudController<Entrenador> {
    private final ClubRepository clubs;
    public EntrenadorController(EntrenadorRepository repo, ClubRepository clubs) { super(repo); this.clubs = clubs; }

    @Override protected void antesDeEliminar(Long id) {
        clubs.findByEntrenadorId(id).forEach(c -> c.setEntrenador(null));
    }
}
