package org.iesvdm.proyecto.exception;

public class CorreoElectronicoException extends RuntimeException {
    public CorreoElectronicoException(String correoElectronico) {
        super("Usuario con este correo:" + correoElectronico + " no encontrado.");
    }
}
