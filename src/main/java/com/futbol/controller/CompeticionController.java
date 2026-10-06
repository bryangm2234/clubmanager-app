package com.futbol.controller;

import com.futbol.model.Competicion;
import com.futbol.repository.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/competiciones")
public class CompeticionController extends CrudController<Competicion> {
    private final ClubRepository clubs;
    public CompeticionController(CompeticionRepository repo, ClubRepository clubs) { super(repo); this.clubs = clubs; }

    @Override protected void antesDeEliminar(Long id) {
        clubs.findByCompeticionesId(id).forEach(c -> c.getCompeticiones().removeIf(x -> x.getId().equals(id)));
    }
}
