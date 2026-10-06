package com.futbol.web;

import com.futbol.controller.CompeticionController;
import com.futbol.model.Competicion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/competiciones")
public class CompeticionWebController extends CrudWebController<Competicion> {

    public CompeticionWebController(CompeticionController api) {
        super(api, "/competiciones", "Competiciones", "Competición", List.of(new String[]{"nombre","Nombre"}, new String[]{"montoPremio","Premio (USD)"}, new String[]{"fechaInicio","Inicio"}, new String[]{"fechaFin","Fin"}, new String[]{"ambito","Ámbito"}), List.of(new String[]{"nombre","Nombre","text"}, new String[]{"montoPremio","Premio (USD)","number"}, new String[]{"fechaInicio","Fecha de inicio","date"}, new String[]{"fechaFin","Fecha de fin","date"}, new String[]{"ambito","Ámbito (país o confederación)","text"}), true);
    }

    @Override protected Competicion nuevo() { return new Competicion(); }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Competicion item, RedirectAttributes ra) { return persistir(item, item.getId(), ra); }
}
