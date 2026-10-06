package com.futbol.web;

import com.futbol.repository.*;
import com.futbol.service.ClubService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ClubService clubs;
    private final JugadorRepository jugadores;
    private final CompeticionRepository competiciones;
    private final AsociacionRepository asociaciones;

    public HomeController(ClubService clubs, JugadorRepository jugadores, CompeticionRepository competiciones, AsociacionRepository asociaciones) {
        this.clubs = clubs; this.jugadores = jugadores; this.competiciones = competiciones; this.asociaciones = asociaciones;
    }

    @GetMapping("/")
    public String inicio(Model m) {
        var lista = clubs.listar();
        m.addAttribute("clubes", lista);
        m.addAttribute("nClubes", lista.size());
        m.addAttribute("nJugadores", jugadores.count());
        m.addAttribute("nComps", competiciones.count());
        m.addAttribute("nAsocs", asociaciones.count());
        return "home";
    }
}
