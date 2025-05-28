package org.iesvdm.proyecto;

import jakarta.transaction.Transactional;
import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Comentario;
import org.iesvdm.proyecto.domain.Inscripcion;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.service.ActividadService;
import org.iesvdm.proyecto.service.ComentarioService;
import org.iesvdm.proyecto.service.InscripcionService;
import org.iesvdm.proyecto.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class IntegracionCompleta {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ActividadService actividadService;
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private InscripcionService inscripcionService;

    @Test
    public void testIntegracionCompleta() {
        // 1. Crear usuarios
        Usuario usuario1 = Usuario.builder()
                .nombre("Juan")
                .apellidos("Pérez")
                .correoElectronico("juan@test.com")
                .contrasena("123abc")
                .build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Ana")
                .apellidos("García")
                .correoElectronico("ana@test.com")
                .contrasena("123abc")
                .build();

        usuario1 = usuarioService.createUsuario(usuario1);
        usuario2 = usuarioService.createUsuario(usuario2);

        // 2. Crear actividades
        Actividad actividad1 = Actividad.builder()
                .nombre("Senderismo")
                .descripcion("Ruta por montaña")
                .pais("Italia")
                .precio(25.0)
                .fecha(LocalDate.now().plusDays(10))
                .imagen("hola")
                .categoria("Senderismo")
                .build();

        Actividad actividad2 = Actividad.builder()
                .nombre("Escalada")
                .descripcion("Escalada en roca")
                .pais("Italia")
                .precio(40.0)
                .fecha(LocalDate.now().plusDays(15))
                .imagen("hola")
                .categoria("Escalada")
                .build();

        actividad1 = actividadService.createActividad(actividad1);
        actividad2 = actividadService.createActividad(actividad2);

        // 3. Crear comentarios
        Comentario comentario1 = Comentario.builder()
                .comentario("¡Excelente actividad!")
                .fechaComentario(LocalDate.now())
                .build();

        Comentario comentario2 = Comentario.builder()
                .comentario("Muy recomendable")
                .fechaComentario(LocalDate.now())
                .build();

        comentarioService.createComentarioByIds(usuario1.getId(), actividad1.getId(), comentario1);
        comentarioService.createComentarioByIds(usuario2.getId(), actividad1.getId(), comentario2);

        // 4. Crear inscripciones
        Inscripcion inscripcion1 = Inscripcion.builder()
                .usuario(usuario1)
                .actividad(actividad1)
                .build();

        Inscripcion inscripcion2 = Inscripcion.builder()
                .usuario(usuario2)
                .actividad(actividad2)
                .build();

        Inscripcion inscripcion3 = Inscripcion.builder()
                .usuario(usuario1)
                .actividad(actividad2)
                .build();

        inscripcionService.createInscripcionByIds(usuario1.getId(), actividad1.getId());
        inscripcionService.createInscripcionByIds(usuario2.getId(), actividad2.getId());
        inscripcionService.createInscripcion(inscripcion3);

        // 5. Verificaciones
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<Actividad> actividades = actividadService.getAllActividades();
        List<Comentario> comentarios = comentarioService.getAllComentarios();
        List<Inscripcion> inscripciones = inscripcionService.getAllInscripciones();

        // Verificar cantidades
        assertEquals(2, usuarios.size());
        assertEquals(2, actividades.size());
        assertEquals(2, comentarios.size());
        assertEquals(3, inscripciones.size());

        // Verificar relaciones de comentarios
        final Long usuario1Id = usuario1.getId();
        final Long actividad1Id = actividad1.getId();
        final Long usuario2Id = usuario2.getId();
        final Long actividad2Id = actividad2.getId();

// Verificar relaciones de comentarios
        assertTrue(comentarios.stream()
                .anyMatch(c -> c.getUsuario().getId().equals(usuario1Id) &&
                               c.getActividad().getId().equals(actividad1Id)));

// Verificar relaciones de inscripciones
        assertTrue(inscripciones.stream()
                .anyMatch(i -> i.getUsuario().getId().equals(usuario1Id) &&
                               i.getActividad().getId().equals(actividad1Id)));

        assertTrue(inscripciones.stream()
                .anyMatch(i -> i.getUsuario().getId().equals(usuario2Id) &&
                               i.getActividad().getId().equals(actividad2Id)));

        assertTrue(inscripciones.stream()
                .anyMatch(i -> i.getUsuario().getId().equals(usuario1Id) &&
                               i.getActividad().getId().equals(actividad2Id)));
    }
}