package com.example.apismisservicios.negocios.models.repositories;

import com.example.apismisservicios.negocios.models.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Persona, Long> {
}
