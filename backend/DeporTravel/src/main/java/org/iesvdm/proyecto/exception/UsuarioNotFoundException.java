package org.iesvdm.proyecto.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("Usuario con ID " + id + " no encontrado.");
    }
}
