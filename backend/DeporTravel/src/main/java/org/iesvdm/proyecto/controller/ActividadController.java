package org.iesvdm.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.*;
import org.iesvdm.proyecto.repository.ActividadRepository;
import org.iesvdm.proyecto.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/actividades")
public class ActividadController {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ActividadService actividadService;

    @GetMapping("/actividadesAll")
    public List<Actividad> getAllActividades() {
        return actividadRepository.findAll();
    }

    @PostMapping
    public Actividad createActividad(@RequestBody Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @GetMapping("/actividadesByNombre")
    public List<Actividad> getActividadesByNombre(@RequestParam String nombre) {
        List<Actividad> actividades = actividadRepository.findByNombre(nombre);
        if (actividades.isEmpty()) {
            throw new NombreNotFoundException(nombre);
        }
        return actividades;
    }

    @GetMapping("/actividadesByFecha")
    public Actividad getActividadesByFecha(@RequestParam LocalDate fecha) {
        return actividadRepository.findByFecha(fecha)
                .orElseThrow(() -> new FechaNotFoundException(fecha));
    }

    @DeleteMapping("/{id}")
    public void deleteActividad(@PathVariable Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ActividadNotFoundException(id));
        actividadRepository.delete(actividad);
    }

    @GetMapping("/{id}")
    public Actividad getActividadById(@PathVariable Long id) {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new ActividadNotFoundException(id));
    }

    @GetMapping("/categorias")
    public List<String> getCategorias() {
        return actividadService.getCategoria();
    }

    @GetMapping("/paises")
    public List<String> getAllPaises() {
        return actividadService.getPais();
    }

}
