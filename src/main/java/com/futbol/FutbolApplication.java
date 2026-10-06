package com.futbol;

import com.futbol.model.*;
import com.futbol.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FutbolApplication {
    public static void main(String[] args) { SpringApplication.run(FutbolApplication.class, args); }

    // Federaciones: {nombre, país, presidente, siglas}
    private static final String[][] FEDERACIONES = {
        {"Federación Colombiana de Fútbol", "Colombia", "Ramón Jesurún", "FCF"},
        {"Real Federación Española de Fútbol", "España", "Rafael Louzán", "RFEF"},
        {"Asociación del Fútbol Argentino", "Argentina", "Claudio Tapia", "AFA"},
        {"Confederação Brasileira de Futebol", "Brasil", "Presidente CBF", "CBF"},
        {"Federación Mexicana de Fútbol", "México", "Presidente FMF", "FMF"},
        {"The Football Association", "Inglaterra", "Presidente FA", "FA"},
        {"Federazione Italiana Giuoco Calcio", "Italia", "Presidente FIGC", "FIGC"},
        {"Deutscher Fußball-Bund", "Alemania", "Presidente DFB", "DFB"},
        {"Fédération Française de Football", "Francia", "Presidente FFF", "FFF"}};

    // Competiciones: {nombre, premio USD, inicio, fin, ámbito}. Ámbito = país (nacional) o confederación (internacional).
    private static final String[][] COMPETICIONES = {
        {"Liga BetPlay Dimayor", "1500000", "2026-01-25", "2026-12-14", "Colombia"},
        {"Copa Colombia", "800000", "2026-03-05", "2026-10-30", "Colombia"},
        {"LaLiga", "3000000", "2026-08-15", "2027-05-24", "España"},
        {"Copa del Rey", "1000000", "2026-10-20", "2027-04-25", "España"},
        {"Liga Profesional Argentina", "1800000", "2026-02-06", "2026-12-07", "Argentina"},
        {"Copa Argentina", "700000", "2026-04-10", "2026-11-15", "Argentina"},
        {"Brasileirão Série A", "2500000", "2026-04-04", "2026-12-06", "Brasil"},
        {"Copa do Brasil", "1500000", "2026-02-18", "2026-11-08", "Brasil"},
        {"Liga MX", "1200000", "2026-07-10", "2026-12-20", "México"},
        {"Premier League", "5000000", "2026-08-15", "2027-05-23", "Inglaterra"},
        {"FA Cup", "2500000", "2026-09-05", "2027-05-15", "Inglaterra"},
        {"Serie A", "3000000", "2026-08-22", "2027-05-30", "Italia"},
        {"Bundesliga", "3000000", "2026-08-28", "2027-05-22", "Alemania"},
        {"Ligue 1", "2000000", "2026-08-14", "2027-05-16", "Francia"},
        {"Copa Libertadores", "20000000", "2026-02-03", "2026-11-28", "CONMEBOL"},
        {"Copa Sudamericana", "8000000", "2026-03-10", "2026-11-21", "CONMEBOL"},
        {"UEFA Champions League", "25000000", "2026-09-15", "2027-05-29", "UEFA"},
        {"UEFA Europa League", "12000000", "2026-09-17", "2027-05-26", "UEFA"},
        {"CONCACAF Champions Cup", "5000000", "2026-02-10", "2026-06-01", "CONCACAF"}};

    // Clubes: {nombre, siglas federación, "entrenador,edad,nacionalidad", "competiciones separadas por ;"}
    private static final String[][] CLUBES = {
        {"Millonarios FC", "FCF", "Gabriel Montoya,52,Colombia", "Liga BetPlay Dimayor;Copa Colombia;Copa Libertadores"},
        {"Atlético Nacional", "FCF", "Hernán Cifuentes,47,Colombia", "Liga BetPlay Dimayor;Copa Libertadores"},
        {"América de Cali", "FCF", "Rodrigo Pastrana,55,Colombia", "Liga BetPlay Dimayor;Copa Colombia;Copa Sudamericana"},
        {"Independiente Santa Fe", "FCF", "Andrés Bohórquez,49,Colombia", "Liga BetPlay Dimayor;Copa Colombia;Copa Sudamericana"},
        {"Junior FC", "FCF", "Sergio Palacio,51,Colombia", "Liga BetPlay Dimayor;Copa Colombia;Copa Libertadores"},
        {"Real Madrid CF", "RFEF", "Alejandro Navas,58,España", "LaLiga;Copa del Rey;UEFA Champions League"},
        {"FC Barcelona", "RFEF", "Marc Ferrer,49,España", "LaLiga;Copa del Rey;UEFA Champions League"},
        {"Atlético de Madrid", "RFEF", "Javier Olmos,54,Argentina", "LaLiga;Copa del Rey;UEFA Champions League"},
        {"Boca Juniors", "AFA", "Martín Acosta,51,Argentina", "Liga Profesional Argentina;Copa Libertadores"},
        {"River Plate", "AFA", "Hernán Giménez,56,Argentina", "Liga Profesional Argentina;Copa Argentina;Copa Libertadores"},
        {"Racing Club", "AFA", "Facundo Bertoni,48,Argentina", "Liga Profesional Argentina;Copa Argentina;Copa Sudamericana"},
        {"Flamengo", "CBF", "Rafael Duarte,53,Brasil", "Brasileirão Série A;Copa do Brasil;Copa Libertadores"},
        {"Palmeiras", "CBF", "Thiago Moreira,50,Portugal", "Brasileirão Série A;Copa do Brasil;Copa Libertadores"},
        {"Club América", "FMF", "Ricardo Lozano,57,México", "Liga MX;CONCACAF Champions Cup"},
        {"CF Monterrey", "FMF", "Fernando Aldama,46,México", "Liga MX;CONCACAF Champions Cup"},
        {"Manchester City", "FA", "Henry Caldwell,55,España", "Premier League;FA Cup;UEFA Champions League"},
        {"Liverpool FC", "FA", "Oliver Whitmore,52,Países Bajos", "Premier League;FA Cup;UEFA Europa League"},
        {"Inter de Milán", "FIGC", "Marco Ferrante,50,Italia", "Serie A;UEFA Champions League"},
        {"Bayern München", "DFB", "Lukas Reinhardt,48,Alemania", "Bundesliga;UEFA Champions League"},
        {"Paris Saint-Germain", "FFF", "Hugo Lefort,53,España", "Ligue 1;UEFA Champions League"}};

    // Nombres de jugadores ficticios según la cultura del país: {nombres, apellidos} (12 cada uno)
    private static final String[][][] NOMBRES = {
        {{"Mateo","Santiago","Andrés","Julián","Camilo","Sebastián","Nicolás","Daniel","Felipe","Tomás","Emilio","Joaquín"},
         {"Ríos","Mendoza","Castaño","Vargas","Quintero","Herrera","Salazar","Paredes","Navarro","Ortega","Campos","Duarte"}},
        {{"Gabriel","Thiago","Rafael","Matheus","Caio","Lucas","Bruno","Vinícius","Leandro","Henrique","Rodrigo","Gustavo"},
         {"Silva","Souza","Oliveira","Santos","Pereira","Costa","Ribeiro","Almeida","Carvalho","Barbosa","Moreira","Teixeira"}},
        {{"Oliver","Harry","Jack","George","Charlie","Thomas","James","Henry","Callum","Jordan","Liam","Connor"},
         {"Walker","Hughes","Bennett","Carter","Foster","Hayes","Parker","Reed","Shaw","Turner","Wood","Barnes"}},
        {{"Marco","Luca","Matteo","Andrea","Davide","Simone","Federico","Alessio","Lorenzo","Riccardo","Giorgio","Stefano"},
         {"Rossi","Bianchi","Conti","Greco","Romano","Ferri","Esposito","Marino","Gallo","Fontana","Moretti","Ricci"}},
        {{"Lukas","Jonas","Felix","Maximilian","Tobias","Florian","Jan","Moritz","Niklas","Leon","Julian","Philipp"},
         {"Müller","Schneider","Fischer","Weber","Becker","Hoffmann","Koch","Richter","Wolf","Neumann","Krüger","Hartmann"}},
        {{"Hugo","Théo","Louis","Mathis","Enzo","Nathan","Antoine","Baptiste","Clément","Maxime","Romain","Quentin"},
         {"Martin","Bernard","Dubois","Thomas","Robert","Petit","Durand","Leroy","Moreau","Simon","Laurent","Lefèvre"}}};
    private static final Map<String, Integer> CULTURA = Map.of("Brasil", 1, "Inglaterra", 2, "Italia", 3, "Alemania", 4, "Francia", 5);
    private static final String[] POS = {"Portero","Defensa","Defensa","Defensa","Defensa","Mediocampista","Mediocampista","Mediocampista","Delantero","Delantero","Delantero"};
    private static final int[] NUM = {1, 2, 3, 4, 5, 6, 8, 10, 7, 9, 11};

    /** Carga datos ficticios de ejemplo. Es idempotente: solo agrega lo que aún no existe. */
    @Bean
    CommandLineRunner datosEjemplo(ClubRepository clubs, AsociacionRepository asocs, CompeticionRepository comps) {
        return args -> {
            Map<String, Asociacion> fed = new HashMap<>();
            for (String[] f : FEDERACIONES) {
                fed.put(f[3], asocs.findByNombre(f[0]).orElseGet(() -> asocs.save(new Asociacion(null, f[0], f[1], f[2], f[3]))));
            }
            Map<String, Competicion> comp = new HashMap<>();
            for (String[] d : COMPETICIONES) {
                Competicion c = comps.findByNombre(d[0]).orElseGet(Competicion::new);
                if (c.getId() == null || !d[4].equals(c.getAmbito())) {   // nueva, o existente sin ámbito
                    c.setNombre(d[0]); c.setMontoPremio(Long.parseLong(d[1]));
                    c.setFechaInicio(LocalDate.parse(d[2])); c.setFechaFin(LocalDate.parse(d[3])); c.setAmbito(d[4]);
                    c = comps.save(c);
                }
                comp.put(d[0], c);
            }
            for (String[] d : CLUBES) {
                if (clubs.existsByNombre(d[0])) continue;
                Club club = new Club();
                club.setNombre(d[0]);
                Asociacion a = fed.get(d[1]);
                club.setAsociacion(a);
                String[] dt = d[2].split(","), nom = dt[0].split(" ", 2);
                club.setEntrenador(new Entrenador(null, nom[0], nom[1], Integer.parseInt(dt[1]), dt[2]));
                String[][] pool = NOMBRES[CULTURA.getOrDefault(a.getPais(), 0)];
                int k = Math.floorMod(d[0].hashCode(), 50);
                for (int i = 0; i < 11; i++) {
                    club.getJugadores().add(new Jugador(null, pool[0][(k + 5 * i) % 12], pool[1][(k * 7 + 7 * i) % 12], NUM[i], POS[i]));
                }
                for (String n : d[3].split(";")) club.getCompeticiones().add(comp.get(n));
                clubs.save(club);
            }
        };
    }
}
