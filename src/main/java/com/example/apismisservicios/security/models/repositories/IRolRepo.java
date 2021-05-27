package com.example.apismisservicios.security.models.repositories;

import com.example.apismisservicios.enums.RolNombre;
import com.example.apismisservicios.security.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepo extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
