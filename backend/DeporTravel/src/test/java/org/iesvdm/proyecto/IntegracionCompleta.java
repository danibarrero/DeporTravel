package org.iesvdm.proyecto;

import jakarta.transaction.Transactional;
import org.iesvdm.proyecto.domain.*;
import org.iesvdm.proyecto.repository.RolRepository;
import org.iesvdm.proyecto.service.ActividadService;
import org.iesvdm.proyecto.service.ComentarioService;
import org.iesvdm.proyecto.service.InscripcionService;
import org.iesvdm.proyecto.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

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
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testIntegracionCompleta() {

        // 1. Crear y guardar roles
        Rol rolAdmin = rolRepository.save(Rol.builder().rol(ERol.ADMIN).build());
        Rol rolUser = rolRepository.save(Rol.builder().rol(ERol.USER).build());

        // 2. Crear usuarios
        Usuario usuario1 = usuarioService.createUsuario(
                Usuario.builder()
                        .nombre("Juan")
                        .apellido("Pérez")
                        .correoElectronico("juan@gmail.com")
                        .contrasena(passwordEncoder.encode("123abc"))
                        .roles(Set.of(rolAdmin))
                        .build());

        Usuario usuario2 = usuarioService.createUsuario(
                Usuario.builder()
                        .nombre("Ana")
                        .apellido("García")
                        .correoElectronico("ana@gmail.com")
                        .contrasena(passwordEncoder.encode("123abc"))
                        .roles(Set.of(rolUser))
                        .build());

        // 3. Crear actividades
        Actividad actividad1 = new Actividad();
        actividad1.setNombre("Yoga en Berlin");
        actividad1.setCategoria("Yoga");
        actividad1.setPais("Berlin");
        actividad1.setPrecio(150.0);
        actividad1.setFecha(LocalDate.parse("2024-10-08"));
        actividad1.setDescripcion("Relájate y encuentra la paz interior mientras disfrutas de la belleza de Berlin.");
        actividad1.setImagen("./img/Actividades/Yoga.jpg");

        Actividad actividad2 = new Actividad();
        actividad2.setNombre("Trekking en Chile");
        actividad2.setCategoria("Trekking");
        actividad2.setPais("Chile");
        actividad2.setPrecio(300.0);
        actividad2.setFecha(LocalDate.parse("2024-12-08"));
        actividad2.setDescripcion("Explora los paisajes de Chile mientras realizas un trekking desafiante por la cordillera de los Andes.");
        actividad2.setImagen("./img/Actividades/Trekking.jpg");

        Actividad actividad3 = new Actividad();
        actividad3.setNombre("Surf en Australia");
        actividad3.setCategoria("Surf");
        actividad3.setPais("Australia");
        actividad3.setPrecio(420.0);
        actividad3.setFecha(LocalDate.parse("2024-08-11"));
        actividad3.setDescripcion("Disfruta de las mejores olas en las playas de Australia, un paraíso para los amantes del surf.");
        actividad3.setImagen("./img/Actividades/Surf.jpg");

        Actividad actividad4 = new Actividad();
        actividad4.setNombre("Montar a caballo en Mallorca");
        actividad4.setCategoria("Montar a caballo");
        actividad4.setPais("España");
        actividad4.setPrecio(120.0);
        actividad4.setFecha(LocalDate.parse("2025-09-08"));
        actividad4.setDescripcion("Recorre los paisajes más hermosos de Mallorca a lomos de un caballo mientras disfrutas de su naturaleza.");
        actividad4.setImagen("./img/Actividades/Montar a caballo.jpg");

        Actividad actividad5 = new Actividad();
        actividad5.setNombre("Ciclismo en los Pirineos");
        actividad5.setCategoria("Ciclismo");
        actividad5.setPais("España");
        actividad5.setPrecio(95.0);
        actividad5.setFecha(LocalDate.parse("2024-04-20"));
        actividad5.setDescripcion("Desafía tus límites en las montañas de los Pirineos mientras disfrutas de vistas impresionantes.");
        actividad5.setImagen("./img/Actividades/Ciclismo.jpg");

        Actividad actividad6 = new Actividad();
        actividad6.setNombre("Alpinismo en los Alpes");
        actividad6.setCategoria("Alpinismo");
        actividad6.setPais("Suiza");
        actividad6.setPrecio(520.0);
        actividad6.setFecha(LocalDate.parse("2024-11-11"));
        actividad6.setDescripcion("Siente la adrenalina del alpinismo en los Alpes suizos, una experiencia desafiante para los más aventureros.");
        actividad6.setImagen("./img/Actividades/Alpinismo.jpg");

        Actividad actividad7 = new Actividad();
        actividad7.setNombre("Karate en China");
        actividad7.setCategoria("Karate");
        actividad7.setPais("China");
        actividad7.setPrecio(852.0);
        actividad7.setFecha(LocalDate.parse("2026-10-08"));
        actividad7.setDescripcion("Perfecciona tus habilidades en Karate en China, el lugar donde nació este antiguo arte marcial.");
        actividad7.setImagen("./img/Actividades/karate.jpg");

        Actividad actividad8 = new Actividad();
        actividad8.setNombre("Fútbol en Estados Unidos");
        actividad8.setCategoria("Fútbol");
        actividad8.setPais("Estados Unidos");
        actividad8.setPrecio(963.0);
        actividad8.setFecha(LocalDate.parse("2025-06-21"));
        actividad8.setDescripcion("Vive la emoción del fútbol en Estados Unidos, donde podrás entrenar y jugar en estadios de clase mundial.");
        actividad8.setImagen("./img/Actividades/Futbol.jpg");

        Actividad actividad9 = new Actividad();
        actividad9.setNombre("Baloncesto en Irlanda");
        actividad9.setCategoria("Baloncesto");
        actividad9.setPais("Irlanda");
        actividad9.setPrecio(453.0);
        actividad9.setFecha(LocalDate.parse("2026-05-19"));
        actividad9.setDescripcion("Mejora tu técnica de baloncesto mientras exploras la cultura y la naturaleza irlandesa.");
        actividad9.setImagen("./img/Actividades/Baloncesto.jpg");

        Actividad actividad10 = new Actividad();
        actividad10.setNombre("Golf en Wales");
        actividad10.setCategoria("Golf");
        actividad10.setPais("Wales");
        actividad10.setPrecio(326.0);
        actividad10.setFecha(LocalDate.parse("2025-11-08"));
        actividad10.setDescripcion("Juega al golf en los campos más hermosos de Gales, rodeado de paisajes naturales impresionantes.");
        actividad10.setImagen("./img/Actividades/Golf.jpg");

        Actividad actividad11 = new Actividad();
        actividad11.setNombre("Kayak en Argentina");
        actividad11.setCategoria("Kayak");
        actividad11.setPais("Argentina");
        actividad11.setPrecio(762.0);
        actividad11.setFecha(LocalDate.parse("2025-06-21"));
        actividad11.setDescripcion("Desciende por los ríos de Argentina en kayak, disfrutando de paisajes de ensueño y una gran aventura.");
        actividad11.setImagen("./img/Actividades/Kayak.jpg");

        Actividad actividad12 = new Actividad();
        actividad12.setNombre("Crossfit en Italia");
        actividad12.setCategoria("Crossfit");
        actividad12.setPais("Italia");
        actividad12.setPrecio(251.0);
        actividad12.setFecha(LocalDate.parse("2024-05-30"));
        actividad12.setDescripcion("Entrena Crossfit en Italia, rodeado de historia y paisajes impresionantes mientras superas tus límites.");
        actividad12.setImagen("./img/Actividades/Crossfit.jpg");

        // 4. Guardar actividades
        actividadService.createActividad(actividad1);
        actividadService.createActividad(actividad2);
        actividadService.createActividad(actividad3);
        actividadService.createActividad(actividad4);
        actividadService.createActividad(actividad5);
        actividadService.createActividad(actividad6);
        actividadService.createActividad(actividad7);
        actividadService.createActividad(actividad8);
        actividadService.createActividad(actividad9);
        actividadService.createActividad(actividad10);
        actividadService.createActividad(actividad11);
        actividadService.createActividad(actividad12);

        // 5. Crear comentarios
        comentarioService.createComentarioByIds(usuario1.getId(), actividad1.getId(),
                Comentario.builder().comentario("¡Excelente actividad!").fechaComentario(LocalDate.now()).build());

        comentarioService.createComentarioByIds(usuario2.getId(), actividad1.getId(),
                Comentario.builder().comentario("Muy recomendable").fechaComentario(LocalDate.now()).build());

        // 6. Crear inscripciones
        inscripcionService.createInscripcionByIds(usuario1.getId(), actividad1.getId());
        inscripcionService.createInscripcionByIds(usuario2.getId(), actividad2.getId());
        inscripcionService.createInscripcion(Inscripcion.builder()
                .usuario(usuario2)
                .actividad(actividad2)
                .build());

        // 7. Verificaciones
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<Actividad> actividades = actividadService.getAllActividades();
        List<Comentario> comentarios = comentarioService.getAllComentarios();
        List<Inscripcion> inscripciones = inscripcionService.getAllInscripciones();

        assertEquals(2, usuarios.size());
        assertEquals(12, actividades.size());
        assertEquals(2, comentarios.size());
        assertEquals(3, inscripciones.size());

        assertTrue(comentarios.stream()
                .anyMatch(c -> c.getUsuario().getId().equals(usuario1.getId()) &&
                        c.getActividad().getId().equals(actividad1.getId())));

        assertTrue(inscripciones.stream()
                .anyMatch(i -> i.getUsuario().getId().equals(usuario1.getId()) &&
                        i.getActividad().getId().equals(actividad1.getId())));
    }
}
