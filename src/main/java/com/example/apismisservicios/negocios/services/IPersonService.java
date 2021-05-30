package com.example.apismisservicios.negocios.services;

import com.example.apismisservicios.negocios.models.entities.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getAll();
    Person getId(Long id);
    Person create(Person p);
    void delete(Long id);

}
