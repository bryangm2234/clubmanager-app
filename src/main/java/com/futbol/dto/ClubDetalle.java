package com.futbol.dto;

import com.futbol.model.*;
import java.util.List;

public record ClubDetalle(Long id, String nombre, Entrenador entrenador, Asociacion asociacion,
                          List<Jugador> jugadores, List<Competicion> competiciones) {}
