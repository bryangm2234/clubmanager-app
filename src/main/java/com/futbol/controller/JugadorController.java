package com.futbol.controller;

import com.futbol.model.Jugador;
import com.futbol.repository.JugadorRepository;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/jugadores")
public class JugadorController extends CrudController<Jugador> {
    public JugadorController(JugadorRepository repo) { super(repo); }
}
