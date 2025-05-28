package org.iesvdm.proyecto.service;

import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Comentario;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.ComentarioNotFoundException;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.ActividadRepository;
import org.iesvdm.proyecto.repository.ComentarioRepository;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    // Obtener todos los comentarios
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findAll();
    }

    // Crear un nuevo comentario
    public Comentario createComentarioByIds(Long idUsuario, Long idActividad, Comentario comentario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException(idUsuario));
        comentario.setUsuario(usuario);

        Actividad actividad = actividadRepository.findById(idActividad)
                .orElseThrow(() -> new ActividadNotFoundException(idActividad));
        comentario.setActividad(actividad);

        return comentarioRepository.save(comentario);
    }

    // Eliminar un comentario
    public void deleteComentario(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ComentarioNotFoundException(id));
        comentarioRepository.delete(comentario);
    }

}
