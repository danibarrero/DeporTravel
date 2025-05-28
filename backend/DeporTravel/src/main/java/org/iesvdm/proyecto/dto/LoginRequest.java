package org.iesvdm.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String correoElectronico;
    private String contrasena;
}
