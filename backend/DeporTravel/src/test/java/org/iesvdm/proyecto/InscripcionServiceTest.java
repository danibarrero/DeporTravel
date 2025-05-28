package org.iesvdm.proyecto;

import org.iesvdm.proyecto.domain.Actividad;
import org.iesvdm.proyecto.domain.Inscripcion;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.repository.ActividadRepository;
import org.iesvdm.proyecto.repository.InscripcionRepository;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.iesvdm.proyecto.service.InscripcionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class InscripcionServiceTest {


    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    private Usuario usuario;
    private Actividad actividad;

    @BeforeEach
    public void setup() {
        inscripcionRepository.deleteAll();
        usuarioRepository.deleteAll();
        actividadRepository.deleteAll();

        usuario = usuarioRepository.save(Usuario.builder()
                .nombre("Carlos")
                .apellidos("Martinez")
                .correoElectronico("carlos@mail.com")
                .contrasena("pass123")
                .build());

        actividad = actividadRepository.save(Actividad.builder()
                .nombre("Senderismo")
                .descripcion("Ruta por la sierra")
                .pais("España")
                .precio(100.0)
                .build());

        Inscripcion inscripcion1 = Inscripcion.builder()
                .usuario(usuario)
                .actividad(actividad)
                .build();


        inscripcionRepository.save(inscripcion1);
    }

//    @Test
//    public void testGetAllInscripciones() {
//        List<Inscripcion> inscripciones = inscripcionService.getAllInscripciones();
//
//        assertNotNull(inscripciones);
//        assertEquals(2, inscripciones.size());
//        assertEquals(usuario.getId(), inscripciones.get(0).getUsuario().getId());
//        assertEquals(actividad.getId(), inscripciones.get(0).getActividad().getId());
//    }

}
