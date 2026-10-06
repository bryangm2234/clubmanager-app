package com.futbol.web;

import com.futbol.dto.*;
import com.futbol.model.*;
import com.futbol.repository.*;
import com.futbol.service.ClubService;
import com.futbol.service.Elegibilidad;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clubes")
public class ClubWebController {
    private final ClubService service;
    private final AsociacionRepository asociaciones;
    private final CompeticionRepository competiciones;

    public ClubWebController(ClubService service, AsociacionRepository asociaciones, CompeticionRepository competiciones) {
        this.service = service; this.asociaciones = asociaciones; this.competiciones = competiciones;
    }

    @GetMapping
    public String lista(Model m) { m.addAttribute("clubes", service.listar()); m.addAttribute("federaciones", asociaciones.findAll()); return "clubes/lista"; }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model m) {
        m.addAttribute("club", service.obtener(id));
        m.addAttribute("jugador", new Jugador());
        return "clubes/detalle";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model m) { return form(m, new ClubForm()); }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model m) {
        ClubDetalle d = service.obtener(id);
        ClubForm f = new ClubForm();
        f.setId(d.id());
        f.setNombre(d.nombre());
        if (d.asociacion() != null) f.setAsociacionId(d.asociacion().getId());
        if (d.entrenador() != null) f.setEntrenador(d.entrenador());
        f.setCompeticionIds(d.competiciones().stream().map(Competicion::getId).toList());
        return form(m, f);
    }

    private String form(Model m, ClubForm f) {
        m.addAttribute("form", f);
        m.addAttribute("asociaciones", asociaciones.findAll());
        m.addAttribute("competiciones", competiciones.findAll());
        Map<Long, String> ambitos = new HashMap<>();
        asociaciones.findAll().forEach(a -> ambitos.put(a.getId(), String.join(",", Elegibilidad.ambitos(a.getPais()))));
        m.addAttribute("ambitos", ambitos);
        return "clubes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ClubForm f, RedirectAttributes ra) {
        Entrenador e = f.getEntrenador();
        boolean hayDT = e != null && e.getNombre() != null && !e.getNombre().isBlank();
        ClubRequest r = new ClubRequest(f.getNombre(), f.getAsociacionId(), hayDT ? e : null, f.getCompeticionIds());
        try {
            ClubDetalle d = f.getId() == null ? service.crear(r) : service.actualizar(f.getId(), r);
            ra.addFlashAttribute("ok", "Club guardado correctamente");
            return "redirect:/clubes/" + d.id();
        } catch (ResponseStatusException ex) {
            ra.addFlashAttribute("error", ex.getReason());
            return "redirect:/clubes/" + (f.getId() == null ? "nuevo" : f.getId() + "/editar");
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        service.eliminar(id);
        ra.addFlashAttribute("ok", "Club eliminado correctamente");
        return "redirect:/clubes";
    }

    @PostMapping("/{id}/jugadores")
    public String agregarJugador(@PathVariable Long id, @ModelAttribute Jugador jugador, RedirectAttributes ra) {
        service.agregarJugador(id, jugador);
        ra.addFlashAttribute("ok", "Jugador agregado");
        return "redirect:/clubes/" + id;
    }

    @PostMapping("/{id}/jugadores/{jugadorId}/eliminar")
    public String quitarJugador(@PathVariable Long id, @PathVariable Long jugadorId, RedirectAttributes ra) {
        service.quitarJugador(id, jugadorId);
        ra.addFlashAttribute("ok", "Jugador eliminado");
        return "redirect:/clubes/" + id;
    }
}
