package com.futbol.repository;

import com.futbol.dto.ClubResumen;
import com.futbol.model.Club;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    // Una sola consulta para el listado (evita N+1)
    @Query("select new com.futbol.dto.ClubResumen(c.id, c.nombre, a.nombre, e.nombre, e.apellido, size(c.jugadores)) " +
           "from Club c left join c.asociacion a left join c.entrenador e order by a.pais, a.nombre, c.nombre")
    List<ClubResumen> resumen();

    // Carga entrenador y asociación en la misma consulta; el resto sigue LAZY
    @EntityGraph(attributePaths = {"entrenador", "asociacion"})
    Optional<Club> findWithDetalleById(Long id);

    boolean existsByNombre(String nombre);
    List<Club> findByEntrenadorId(Long id);
    List<Club> findByAsociacionId(Long id);
    List<Club> findByCompeticionesId(Long id);
}
