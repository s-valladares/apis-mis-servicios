package com.example.apismisservicios.negocios.services;

import com.example.apismisservicios.negocios.models.entities.Persona;

import java.util.List;

public interface IPersonService {
    List<Persona> getAll();
    Persona getId(Long id);
    Persona create(Persona p);
    void delete(Long id);

}
