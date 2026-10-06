package com.futbol.repository;

import com.futbol.model.Asociacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsociacionRepository extends JpaRepository<Asociacion, Long> {
    java.util.Optional<Asociacion> findByNombre(String nombre);
}
