package org.iesvdm.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String apellidos;
    private String contrasena;
    private String correoElectronico;
    private Set<String> roles;
}
