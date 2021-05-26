package com.example.apismisservicios.security.models.repositories;

import com.example.apismisservicios.security.enums.RolNombre;
import com.example.apismisservicios.security.models.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRolRepo extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
