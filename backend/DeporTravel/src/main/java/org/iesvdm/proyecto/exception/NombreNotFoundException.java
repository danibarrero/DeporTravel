package org.iesvdm.proyecto.exception;

public class NombreNotFoundException extends RuntimeException {
    public NombreNotFoundException(String nombre) {
        super("Actividad con nombre: " + nombre + " no encontrado");
    }
}
