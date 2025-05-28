package org.iesvdm.proyecto.exception;

public class PaisNotFoundException extends RuntimeException {
    public PaisNotFoundException(String pais) {
        super("Actividad con pais: " + pais + " no encontrado");
    }
}
