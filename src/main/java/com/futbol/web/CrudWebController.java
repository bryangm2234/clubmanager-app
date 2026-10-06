package com.futbol.web;

import com.futbol.controller.CrudController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/** Controlador de vistas genérico: listar, nuevo, editar y eliminar. Delega en el controlador REST de la entidad. */
public abstract class CrudWebController<T> {
    private final CrudController<T> api;
    private final String base, titulo, singular;
    private final List<String[]> cols, campos;
    private final boolean permiteNuevo;

    protected CrudWebController(CrudController<T> api, String base, String titulo, String singular,
                                List<String[]> cols, List<String[]> campos, boolean permiteNuevo) {
        this.api = api; this.base = base; this.titulo = titulo; this.singular = singular;
        this.cols = cols; this.campos = campos; this.permiteNuevo = permiteNuevo;
    }

    protected abstract T nuevo();

    private void meta(Model m) {
        m.addAttribute("base", base); m.addAttribute("titulo", titulo); m.addAttribute("singular", singular);
        m.addAttribute("cols", cols); m.addAttribute("campos", campos); m.addAttribute("permiteNuevo", permiteNuevo);
    }

    @GetMapping
    public String lista(Model m) { meta(m); m.addAttribute("items", api.listar()); return "crud/lista"; }

    @GetMapping("/nuevo")
    public String formNuevo(Model m) { meta(m); m.addAttribute("item", nuevo()); return "crud/form"; }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model m) { meta(m); m.addAttribute("item", api.obtener(id)); return "crud/form"; }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try { api.eliminar(id); ra.addFlashAttribute("ok", singular + " eliminado/a correctamente"); }
        catch (Exception e) { ra.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage()); }
        return "redirect:" + base;
    }

    protected String persistir(T item, Long id, RedirectAttributes ra) {
        try {
            if (id == null) api.crear(item); else api.actualizar(id, item);
            ra.addFlashAttribute("ok", singular + " guardado/a correctamente");
        } catch (Exception e) { ra.addFlashAttribute("error", "No se pudo guardar: " + e.getMessage()); }
        return "redirect:" + base;
    }
}
