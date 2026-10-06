package com.futbol.web;

import com.futbol.model.Entrenador;
import java.util.ArrayList;
import java.util.List;

/** Objeto de formulario para crear/editar un club desde la vista. */
public class ClubForm {
    private Long id;
    private String nombre;
    private Long asociacionId;
    private Entrenador entrenador = new Entrenador();
    private List<Long> competicionIds = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getAsociacionId() { return asociacionId; }
    public void setAsociacionId(Long asociacionId) { this.asociacionId = asociacionId; }
    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }
    public List<Long> getCompeticionIds() { return competicionIds; }
    public void setCompeticionIds(List<Long> competicionIds) { this.competicionIds = competicionIds; }
}
