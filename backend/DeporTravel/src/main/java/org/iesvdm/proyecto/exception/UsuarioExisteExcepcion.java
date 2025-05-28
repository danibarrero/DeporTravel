package org.iesvdm.proyecto.exception;

public class UsuarioExisteExcepcion extends RuntimeException {
    public UsuarioExisteExcepcion(String correoElectronico) {
        super("El correo electrónico '" + correoElectronico + "' ya está registrado.");
    }
}
