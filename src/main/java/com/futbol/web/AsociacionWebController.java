package com.futbol.web;

import com.futbol.controller.AsociacionController;
import com.futbol.model.Asociacion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/asociaciones")
public class AsociacionWebController extends CrudWebController<Asociacion> {

    public AsociacionWebController(AsociacionController api) {
        super(api, "/asociaciones", "Asociaciones", "Asociación", List.of(new String[]{"nombre","Nombre"}, new String[]{"siglas","Siglas"}, new String[]{"pais","País"}, new String[]{"presidente","Presidente"}), List.of(new String[]{"nombre","Nombre","text"}, new String[]{"siglas","Siglas","text"}, new String[]{"pais","País","text"}, new String[]{"presidente","Presidente","text"}), true);
    }

    @Override protected Asociacion nuevo() { return new Asociacion(); }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Asociacion item, RedirectAttributes ra) { return persistir(item, item.getId(), ra); }
}
