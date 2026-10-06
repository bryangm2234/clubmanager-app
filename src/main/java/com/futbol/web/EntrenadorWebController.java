package com.futbol.web;

import com.futbol.controller.EntrenadorController;
import com.futbol.model.Entrenador;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorWebController extends CrudWebController<Entrenador> {

    public EntrenadorWebController(EntrenadorController api) {
        super(api, "/entrenadores", "Entrenadores", "Entrenador", List.of(new String[]{"nombre","Nombre"}, new String[]{"apellido","Apellido"}, new String[]{"edad","Edad"}, new String[]{"nacionalidad","Nacionalidad"}), List.of(new String[]{"nombre","Nombre","text"}, new String[]{"apellido","Apellido","text"}, new String[]{"edad","Edad","number"}, new String[]{"nacionalidad","Nacionalidad","text"}), true);
    }

    @Override protected Entrenador nuevo() { return new Entrenador(); }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Entrenador item, RedirectAttributes ra) { return persistir(item, item.getId(), ra); }
}
