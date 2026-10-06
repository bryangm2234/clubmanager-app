package com.futbol.dto;

import com.futbol.model.Entrenador;
import java.util.List;

public record ClubRequest(String nombre, Long asociacionId, Entrenador entrenador, List<Long> competicionIds) {}
