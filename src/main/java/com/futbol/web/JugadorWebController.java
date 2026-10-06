package com.futbol.web;

import com.futbol.controller.JugadorController;
import com.futbol.model.Jugador;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/jugadores")
public class JugadorWebController extends CrudWebController<Jugador> {

    public JugadorWebController(JugadorController api) {
        super(api, "/jugadores", "Jugadores", "Jugador", List.of(new String[]{"numero","N°"}, new String[]{"nombre","Nombre"}, new String[]{"apellido","Apellido"}, new String[]{"posicion","Posición"}), List.of(new String[]{"nombre","Nombre","text"}, new String[]{"apellido","Apellido","text"}, new String[]{"numero","Número","number"}, new String[]{"posicion","Posición","text"}), false);
    }

    @Override protected Jugador nuevo() { return new Jugador(); }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Jugador item, RedirectAttributes ra) { return persistir(item, item.getId(), ra); }
}
