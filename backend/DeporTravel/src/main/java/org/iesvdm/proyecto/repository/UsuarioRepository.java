package org.iesvdm.proyecto.repository;

import org.iesvdm.proyecto.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Filtra por correo electrónico
    @Query("SELECT us FROM Usuario us WHERE us.correoElectronico = :correoElectronico")
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    Boolean existsByNombre(String nombre);

    Boolean existsByCorreoElectronico(String correoElectronico);
}