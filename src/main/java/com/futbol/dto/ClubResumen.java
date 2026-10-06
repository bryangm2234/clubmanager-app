package com.futbol.dto;

public record ClubResumen(Long id, String nombre, String asociacion,
                          String entrenadorNombre, String entrenadorApellido, Integer jugadores) {}
