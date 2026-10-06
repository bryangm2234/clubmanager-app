package com.futbol.service;

import com.futbol.dto.*;
import com.futbol.model.*;
import com.futbol.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClubService {
    private final ClubRepository clubs;
    private final AsociacionRepository asociaciones;
    private final CompeticionRepository competiciones;

    public ClubService(ClubRepository clubs, AsociacionRepository asociaciones, CompeticionRepository competiciones) {
        this.clubs = clubs; this.asociaciones = asociaciones; this.competiciones = competiciones;
    }

    @Transactional(readOnly = true)
    public List<ClubResumen> listar() { return clubs.resumen(); }

    @Transactional(readOnly = true)
    public ClubDetalle obtener(Long id) { return detalle(buscar(id)); }

    public ClubDetalle crear(ClubRequest r) { return detalle(clubs.save(aplicar(new Club(), r))); }

    public ClubDetalle actualizar(Long id, ClubRequest r) { return detalle(aplicar(buscar(id), r)); }

    public void eliminar(Long id) { clubs.delete(buscar(id)); }

    public ClubDetalle agregarJugador(Long id, Jugador j) {
        Club c = buscar(id);
        j.setId(null);
        c.getJugadores().add(j);
        clubs.flush();
        return detalle(c);
    }

    public ClubDetalle quitarJugador(Long id, Long jugadorId) {
        Club c = buscar(id);
        c.getJugadores().removeIf(j -> j.getId().equals(jugadorId));
        return detalle(c);
    }

    private Club buscar(Long id) {
        return clubs.findWithDetalleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club no encontrado"));
    }

    private Club aplicar(Club c, ClubRequest r) {
        c.setNombre(r.nombre());
        c.setAsociacion(r.asociacionId() == null ? null : asociaciones.findById(r.asociacionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asociación inválida")));
        if (r.entrenador() != null) {
            Entrenador e = c.getEntrenador() != null ? c.getEntrenador() : new Entrenador();
            e.setNombre(r.entrenador().getNombre());
            e.setApellido(r.entrenador().getApellido());
            e.setEdad(r.entrenador().getEdad());
            e.setNacionalidad(r.entrenador().getNacionalidad());
            c.setEntrenador(e);
        }
        List<Competicion> lista = r.competicionIds() == null ? new ArrayList<>()
                : new ArrayList<>(competiciones.findAllById(r.competicionIds()));
        for (Competicion k : lista) {
            if (!Elegibilidad.puede(k, c.getAsociacion())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "«" + c.getNombre() + "» no puede participar en «"
                        + k.getNombre() + "»: solo admite clubes de " + k.getAmbito() + " (según su asociación).");
            }
        }
        c.setCompeticiones(lista);
        return c;
    }

    private ClubDetalle detalle(Club c) {
        return new ClubDetalle(c.getId(), c.getNombre(), c.getEntrenador(), c.getAsociacion(),
                new ArrayList<>(c.getJugadores()), new ArrayList<>(c.getCompeticiones()));
    }
}
