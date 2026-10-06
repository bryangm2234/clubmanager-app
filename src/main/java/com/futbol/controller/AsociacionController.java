package com.futbol.controller;

import com.futbol.model.Asociacion;
import com.futbol.repository.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/asociaciones")
public class AsociacionController extends CrudController<Asociacion> {
    private final ClubRepository clubs;
    public AsociacionController(AsociacionRepository repo, ClubRepository clubs) { super(repo); this.clubs = clubs; }

    @Override protected void antesDeEliminar(Long id) {
        clubs.findByAsociacionId(id).forEach(c -> c.setAsociacion(null));
    }
}
