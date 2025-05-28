package org.iesvdm.proyecto;

import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.iesvdm.proyecto.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void clean() {
        usuarioRepository.deleteAll();
    }

    // Test para listar todos los usuarios
    @Test
    public void testCreateUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("Juan")
                .apellidos("Perez")
                .correoElectronico("juan@mail.com")
                .contrasena("123abc")
                .build();

        Usuario savedUsuario = usuarioService.createUsuario(usuario);

        assertNotNull(savedUsuario.getId());
        assertEquals("Juan", savedUsuario.getNombre());
    }

    // Test para listar todos los usuarios asegurando que devuelve los creados
    @Test
    public void testGetAllUsuarios() {
        Usuario usuario1 = Usuario.builder()
                .nombre("Juan")
                .apellidos("Perez")
                .correoElectronico("juan@mail.com")
                .contrasena("123abc")
                .build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Ana")
                .apellidos("Gomez")
                .correoElectronico("ana@mail.com")
                .contrasena("123abc")
                .build();

        usuarioService.createUsuario(usuario1);
        usuarioService.createUsuario(usuario2);

        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        assertFalse(usuarios.isEmpty());
        assertEquals(2, usuarios.size());
    }

    // Test para actualizar un usuario
    @Test
    public void testUpdateUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("Miguel")
                .apellidos("Hernandez")
                .correoElectronico("miguel@mail.com")
                .contrasena("123abc")
                .build();

        Usuario savedUsuario = usuarioService.createUsuario(usuario);

        Usuario updateDetails = new Usuario();
        updateDetails.setNombre("Miguel");
        updateDetails.setApellidos("Barrero");
        updateDetails.setCorreoElectronico(savedUsuario.getCorreoElectronico());
        updateDetails.setContrasena("456xyz");

        Optional<Usuario> updatedUsuarioOpt = usuarioService.updateUsuario(savedUsuario.getId(), updateDetails);

        assertTrue(updatedUsuarioOpt.isPresent(), "El usuario no se encontró después de la actualización");

        Usuario updatedUsuario = updatedUsuarioOpt.get();

        assertEquals("Barrero", updatedUsuario.getApellidos(), "El apellido no se actualizó correctamente");
        assertEquals("456xyz", updatedUsuario.getContrasena(), "La contraseña no se actualizó correctamente");
    }

    // Test para eliminar un usuario
    @Test
    public void testDeleteUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("Miguel")
                .apellidos("Hernandez")
                .correoElectronico("miguel@mail.com")
                .contrasena("123abc")
                .build();

        Usuario savedUsuario = usuarioService.createUsuario(usuario);
        Long usuarioId = savedUsuario.getId();

        assertTrue(usuarioService.getUsuarioById(usuarioId).isPresent());

        usuarioService.deleteUsuario(usuarioId);

        Optional<Usuario> deletedUsuario = usuarioService.getUsuarioById(usuarioId);
        assertFalse(deletedUsuario.isPresent());
    }

    // Test para obtener un usuario por ID
    @Test
    public void testGetUsuarioById() {
        Usuario usuario = Usuario.builder()
                .nombre("Carlos")
                .apellidos("Ramirez")
                .correoElectronico("carlos@mail.com")
                .contrasena("123abc")
                .build();

        Usuario savedUsuario = usuarioService.createUsuario(usuario);

        Optional<Usuario> foundUsuario = usuarioService.getUsuarioById(savedUsuario.getId());

        assertTrue(foundUsuario.isPresent());
        assertEquals("Carlos", foundUsuario.get().getNombre());
    }

}

