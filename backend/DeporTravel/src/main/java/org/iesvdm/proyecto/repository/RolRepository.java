package org.iesvdm.proyecto.repository;

import org.iesvdm.proyecto.domain.ERol;
import org.iesvdm.proyecto.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRol(ERol rol);
}
