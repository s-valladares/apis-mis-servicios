package com.example.apismisservicios.negocios.models.repositories;

import com.example.apismisservicios.security.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Usuario, Long> {
}
