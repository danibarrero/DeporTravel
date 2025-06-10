package org.iesvdm.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String contrasena;

    @NotBlank
    @Email
    private String correoElectronico;

    private Set<String> roles;
}
