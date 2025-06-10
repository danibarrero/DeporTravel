package org.iesvdm.proyecto.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.UsuarioExisteExcepcion;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario createUsuario(Usuario usuario) {
        if(usuario.getId()==null) {
            return usuarioRepository.save(usuario);
        } else {
            return usuarioRepository.findById(usuario.getId()).
                    orElseThrow(() -> new UsuarioNotFoundException(usuario.getId()));
        }
    }

    public Optional<Usuario> updateUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setApellido(usuarioDetails.getApellido());
            usuario.setCorreoElectronico(usuarioDetails.getCorreoElectronico());
            usuario.setContrasena(usuarioDetails.getContrasena());
            return usuarioRepository.save(usuario);
        });
    }

    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

    // Filtrar por correo electrónico
    public Optional<Usuario> getUsuarioBycorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }

    // Filtar por Id
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public void cambiarContrasena(Long id, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);
    }

}

