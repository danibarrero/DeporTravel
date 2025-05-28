package org.iesvdm.proyecto.repository;

import org.iesvdm.proyecto.domain.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    // Filtra por nombre
    @Query("SELECT ac FROM Actividad ac WHERE ac.nombre = :nombre")
    List<Actividad> findByNombre(String nombre);

    // Filtra por pais con paginación
    @Query("SELECT ac FROM Actividad ac WHERE ac.pais = :pais")
    Page<Actividad> findByPais(String pais, Pageable pageable);

    // Filtrar por fecha
    @Query("SELECT a FROM Actividad a WHERE a.fecha = :fecha")
    Optional<Actividad> findByFecha(LocalDate fecha);
}
