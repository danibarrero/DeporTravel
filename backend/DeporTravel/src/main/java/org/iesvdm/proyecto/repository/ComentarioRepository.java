package org.iesvdm.proyecto.repository;

import org.iesvdm.proyecto.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByActividadId(Long idActividad);
}