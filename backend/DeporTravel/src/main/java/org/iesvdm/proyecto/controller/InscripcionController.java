package org.iesvdm.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Inscripcion;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.InscripcionRepository;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.iesvdm.proyecto.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/inscripcionesAll")
    public List<Inscripcion> getAllInscripciones() {
        return inscripcionRepository.findAll();
    }

    @PostMapping("/{idUsuario}/{idActividad}")
    public Inscripcion createInscripcion(
            @PathVariable Long idUsuario,
            @PathVariable Long idActividad) {
        Inscripcion inscripcion = new Inscripcion();
        return inscripcionService.createInscripcionByIds(idUsuario, idActividad);
    }

    @GetMapping("/exists/{idUsuario}/{idActividad}")
    public boolean existeInscripcion(
            @PathVariable Long idUsuario,
            @PathVariable Long idActividad) {
        return inscripcionService.existeInscripcion(idUsuario, idActividad);
    }

    @GetMapping("/actividadesPorUsuario/{idUsuario}")
    public List<Actividad> getActividadesPorUsuario(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException(idUsuario));
        return inscripcionService.findActividadesByUsuarioId(idUsuario);
    }

}
