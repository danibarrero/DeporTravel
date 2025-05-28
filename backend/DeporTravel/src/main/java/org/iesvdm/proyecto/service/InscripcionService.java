package org.iesvdm.proyecto.service;

import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Comentario;
import org.iesvdm.proyecto.domain.Inscripcion;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.ActividadRepository;
import org.iesvdm.proyecto.repository.InscripcionRepository;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {


    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Inscripcion> getAllInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion createInscripcion(Inscripcion inscripcion) {
        // Verificar que el usuario existe
        Usuario usuario = usuarioRepository.findById(inscripcion.getUsuario().getId())
                .orElseThrow(() -> new UsuarioNotFoundException(inscripcion.getUsuario().getId()));

        // Verificar que la actividad existe
        Actividad actividad = actividadRepository.findById(inscripcion.getActividad().getId())
                .orElseThrow(() -> new ActividadNotFoundException(inscripcion.getActividad().getId()));

        // Asignar usuario y actividad verificados
        inscripcion.setUsuario(usuario);
        inscripcion.setActividad(actividad);

        return inscripcionRepository.save(inscripcion);
    }

    // Metodo  que acepta IDs
    public Inscripcion createInscripcionByIds(Long idUsuario, Long idActividad) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException(idUsuario));

        Actividad actividad = actividadRepository.findById(idActividad)
                .orElseThrow(() -> new ActividadNotFoundException(idActividad));

        Inscripcion inscripcion = Inscripcion.builder()
                .usuario(usuario)
                .actividad(actividad)
                .build();

        return inscripcionRepository.save(inscripcion);
    }

}