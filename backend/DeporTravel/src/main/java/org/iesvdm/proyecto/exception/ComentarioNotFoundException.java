package org.iesvdm.proyecto.exception;

public class ComentarioNotFoundException extends RuntimeException {
    public ComentarioNotFoundException(Long id) {
        super("Comentario con ID: " + id + " no encontrado");
    }
}
