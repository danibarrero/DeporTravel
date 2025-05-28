package org.iesvdm.proyecto.service;

import jakarta.transaction.Transactional;
import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.ComentarioNotFoundException;
import org.iesvdm.proyecto.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Transactional
    public List<Actividad> getAllActividades() {
        return actividadRepository.findAll();
    }

    public Actividad createActividad(Actividad actividad) {
        if(actividad.getId()==null) {
            return actividadRepository.save(actividad);
        } else {
            return actividadRepository.findById(actividad.getId()).
                    orElseThrow(() -> new ActividadNotFoundException(actividad.getId()));
        }
    }

    public void deleteActividad(Long id) {
        actividadRepository.deleteById(id);
    }

    // Filtrar por nombre
    public List<Actividad> getActividadesByNombre(String nombre) {
        return actividadRepository.findByNombre(nombre);
    }

    // Filtrar por país con paginación
    public Page<Actividad> getActividadesByPaisPaginable(String pais, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return actividadRepository.findByPais(pais, pageable);
    }

    // Filtrar por fecha
    public Optional<Actividad> getActividadesByFecha(LocalDate fecha) {
        return actividadRepository.findByFecha(fecha);
    }
}
