package org.iesvdm.proyecto.exception;

public class ActividadNotFoundException extends RuntimeException {
    public ActividadNotFoundException(Long id) {
        super("Actividad con ID " + id + " no encontrado.");
    }
}
