package org.iesvdm.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    @Email
    private String correoElectronico;

    @NotBlank
    @Size(min = 6)
    private String contrasena;
}
