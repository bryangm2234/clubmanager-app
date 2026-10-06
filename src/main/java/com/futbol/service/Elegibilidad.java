package com.futbol.service;

import com.futbol.model.Asociacion;
import com.futbol.model.Competicion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Un club solo puede jugar torneos de su país (federación) o de su confederación. */
public final class Elegibilidad {
    private static final Map<String, String> CONFEDERACION = Map.ofEntries(
            Map.entry("Colombia", "CONMEBOL"), Map.entry("Argentina", "CONMEBOL"), Map.entry("Brasil", "CONMEBOL"),
            Map.entry("España", "UEFA"), Map.entry("Inglaterra", "UEFA"), Map.entry("Italia", "UEFA"),
            Map.entry("Alemania", "UEFA"), Map.entry("Francia", "UEFA"), Map.entry("México", "CONCACAF"));

    private Elegibilidad() {}

    public static List<String> ambitos(String pais) {
        List<String> l = new ArrayList<>();
        if (pais != null) { l.add(pais); String c = CONFEDERACION.get(pais); if (c != null) l.add(c); }
        return l;
    }

    public static boolean puede(Competicion c, Asociacion a) {
        if (c.getAmbito() == null || c.getAmbito().isBlank()) return true;
        return a != null && ambitos(a.getPais()).stream().anyMatch(x -> x.equalsIgnoreCase(c.getAmbito()));
    }
}
