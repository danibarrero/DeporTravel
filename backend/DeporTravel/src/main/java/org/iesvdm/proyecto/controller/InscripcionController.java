package org.iesvdm.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.proyecto.domain.Inscripcion;
import org.iesvdm.proyecto.repository.InscripcionRepository;
import org.iesvdm.proyecto.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/v1/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping("/inscripcionesAll")
    public List<Inscripcion> getAllInscripciones() {
        return inscripcionRepository.findAll();
    }

//    @PostMapping("/{idUsuario}/{idActividad}")
//    public Inscripcion createInscripcion(
//            @PathVariable Long idUsuario,
//            @PathVariable Long idActividad) {
//        Inscripcion inscripcion = new Inscripcion();
//        return inscripcionService.createInscipcion(idUsuario, idActividad, inscripcion);
//    }

}
