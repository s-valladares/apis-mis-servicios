package com.example.apismisservicios.security.services;

import com.example.apismisservicios.security.models.entities.Usuario;
import com.example.apismisservicios.security.models.repositories.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService{

    private final IUserRepo userRepo;
    @Autowired
    public UserService(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return userRepo.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return userRepo.existsByNombreUsuario(nombreUsuario);
    }

    public Usuario save(Usuario usuario){
        return userRepo.save(usuario);
    }

}
