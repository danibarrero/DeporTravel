package org.iesvdm.proyecto.exception;

import java.time.LocalDate;

public class FechaNotFoundException extends RuntimeException {
    public FechaNotFoundException(LocalDate fecha) {
        super("Actividad con fecha: " + fecha + " no encontrado");
    }
}
