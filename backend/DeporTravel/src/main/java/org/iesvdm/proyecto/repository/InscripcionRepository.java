package org.iesvdm.proyecto.repository;

import org.iesvdm.proyecto.domain.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByUsuarioIdAndActividadId(Long usuarioId, Long actividadId);

    List<Inscripcion> findByUsuarioId(Long usuarioId);

}