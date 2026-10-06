package com.futbol.repository;

import com.futbol.model.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompeticionRepository extends JpaRepository<Competicion, Long> {
    java.util.Optional<Competicion> findByNombre(String nombre);
}
