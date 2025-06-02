package org.iesvdm.proyecto.controller;

import jakarta.validation.Valid;
import org.iesvdm.proyecto.domain.ERol;
import org.iesvdm.proyecto.domain.MessageResponse;
import org.iesvdm.proyecto.domain.Rol;
import org.iesvdm.proyecto.domain.Usuario;
import org.iesvdm.proyecto.dto.LoginRequest;
import org.iesvdm.proyecto.dto.RegisterRequest;
import org.iesvdm.proyecto.repository.RolRepository;
import org.iesvdm.proyecto.repository.UsuarioRepository;
import org.iesvdm.proyecto.security.TokenUtils;
import org.iesvdm.proyecto.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenUtils tokenUtils;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<Usuario> usuarioOptional = userRepository.findByCorreoElectronico(loginRequest.getCorreoElectronico());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOptional.get();

        if (!encoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(401).body(Map.of("message", "Contraseña incorrecta"));
        }

        // Usuario y contraseña correctos, crear token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreoElectronico(), loginRequest.getContrasena())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", userDetails.getId());
        response.put("nombre", userDetails.getNombre());
        response.put("apellidos", userDetails.getApellidos());
        response.put("correoElectronico", userDetails.getCorreoElectronico());
        response.put("roles", roles);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByNombre(registerRequest.getNombre())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username ya en uso!"));
        }

        if (userRepository.existsByCorreoElectronico(registerRequest.getCorreoElectronico())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email ya en uso!"));
        }

        // Create new user's account
        Usuario user = new Usuario(registerRequest.getNombre(),
                registerRequest.getApellido(),
                registerRequest.getCorreoElectronico(),
                encoder.encode(registerRequest.getContrasena())
        );

        Set<String> strRoles = registerRequest.getRoles();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRole = rolRepository.findByRol(ERol.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Rol adminRole = rolRepository.findByRol(ERol.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRole);

                        break;

                    default:
                        Rol userRole = rolRepository.findByRol(ERol.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente!"));
    }

}

