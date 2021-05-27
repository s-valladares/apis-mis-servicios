package com.example.apismisservicios.security.services;

import com.example.apismisservicios.enums.RolNombre;
import com.example.apismisservicios.security.models.entities.Rol;
import com.example.apismisservicios.security.models.repositories.IRolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    private final IRolRepo rolRepo;
    @Autowired
    public RolService(IRolRepo rolRepo){
        this.rolRepo = rolRepo;
    }

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepo.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepo.save(rol);
    }
}
