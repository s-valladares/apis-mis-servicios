package com.example.apismisservicios.negocios.models.repositories;

import com.example.apismisservicios.negocios.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
}
