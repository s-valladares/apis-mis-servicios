package com.example.apismisservicios.negocios.services.implemets;

import com.example.apismisservicios.negocios.models.entities.Person;
import com.example.apismisservicios.negocios.models.repositories.IPersonRepository;
import com.example.apismisservicios.negocios.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PersonaImpl implements IPersonService {

    final IPersonRepository personRepository;
    @Autowired
    public PersonaImpl(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person getId(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person create(Person p) {
        return personRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
