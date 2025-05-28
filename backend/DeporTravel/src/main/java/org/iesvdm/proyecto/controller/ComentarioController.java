package org.iesvdm.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Comentario;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.ComentarioNotFoundException;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.ComentarioRepository;
import org.iesvdm.proyecto.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/v1/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/comentariosAll")
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteComentario(@PathVariable Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ComentarioNotFoundException(id));
        comentarioRepository.delete(comentario);
    }

    @PostMapping("/{idUsuario}/{idActividad}")
    public Comentario createComentario(
            @PathVariable Long idUsuario,
            @PathVariable Long idActividad,
            @RequestBody Comentario comentario) {
        return comentarioService.createComentarioByIds(idUsuario, idActividad, comentario);
    }

}
