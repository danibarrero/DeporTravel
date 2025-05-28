package org.iesvdm.proyecto;

import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Comentario;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.ActividadRepository;
import org.iesvdm.proyecto.repository.ComentarioRepository;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.iesvdm.proyecto.service.ComentarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ComentarioServiceTest {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @BeforeEach
    void setUp() {
        comentarioRepository.deleteAll();
        usuarioRepository.deleteAll();
        actividadRepository.deleteAll();

        Usuario usuario = Usuario.builder()
                .nombre("Juan")
                .apellidos("Pérez")
                .correoElectronico("juan@mail.com")
                .contrasena("password123")
                .build();
        usuarioRepository.save(usuario);

        Actividad actividad = Actividad.builder()
                .nombre("Excursión")
                .descripcion("Caminata en la montaña")
                .pais("España")
                .precio(20.0)
                .build();
        actividadRepository.save(actividad);
    }

    @Test
    void testGetAllComentarios() {
        Usuario usuario = usuarioRepository.findAll().get(0);
        Actividad actividad = actividadRepository.findAll().get(0);

        Comentario comentario1 = Comentario.builder()
                .comentario("¡Gran experiencia!")
                .usuario(usuario)
                .actividad(actividad)
                .build();
        comentarioRepository.save(comentario1);

        Comentario comentario2 = Comentario.builder()
                .comentario("¡Me encantó!")
                .usuario(usuario)
                .actividad(actividad)
                .build();
        comentarioRepository.save(comentario2);

        List<Comentario> comentarios = comentarioService.getAllComentarios();

        assertNotNull(comentarios);
        assertEquals(2, comentarios.size());
        assertEquals("¡Gran experiencia!", comentarios.get(0).getComentario());
        assertEquals("¡Me encantó!", comentarios.get(1).getComentario());
    }

    @Test
    void testCreateComentarioByIds() {
        Usuario usuario = usuarioRepository.findAll().get(0);
        Actividad actividad = actividadRepository.findAll().get(0);

        Comentario comentario = Comentario.builder()
                .comentario("¡Actividad fantástica!")
                .build();

        Comentario resultado = comentarioService.createComentarioByIds(usuario.getId(), actividad.getId(), comentario);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("¡Actividad fantástica!", resultado.getComentario());
        assertEquals(usuario.getId(), resultado.getUsuario().getId());
        assertEquals(actividad.getId(), resultado.getActividad().getId());
    }

    @Test
    void testDeleteComentario() {
        Usuario usuario = usuarioRepository.findAll().get(0);
        Actividad actividad = actividadRepository.findAll().get(0);

        Comentario comentario = Comentario.builder()
                .comentario("¡Gran actividad!")
                .usuario(usuario)
                .actividad(actividad)
                .build();
        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        assertTrue(comentarioRepository.findById(comentarioGuardado.getId()).isPresent());

        comentarioService.deleteComentario(comentarioGuardado.getId());

        assertFalse(comentarioRepository.findById(comentarioGuardado.getId()).isPresent());
    }

}
