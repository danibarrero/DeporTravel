package org.iesvdm.proyecto.service;

import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.exception.ActividadNotFoundException;
import org.iesvdm.proyecto.exception.UsuarioExisteExcepcion;
import org.iesvdm.proyecto.exception.UsuarioNotFoundException;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
            usuario.setApellidos(usuarioDetails.getApellidos());
            usuario.setCorreoElectronico(usuarioDetails.getCorreoElectronico());
            usuario.setContrasena(usuarioDetails.getContrasena());
            return usuarioRepository.save(usuario);
        });
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Filtrar por correo electrónico
    public Optional<Usuario> getUsuarioBycorreoElectronico(String correoElectronico) {
        return usuarioRepository.findByCorreoElectronico(correoElectronico);
    }

    // Filtar por Id
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

}

