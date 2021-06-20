package com.example.apismisservicios.negocios.services.implemets;


import com.example.apismisservicios.negocios.models.repositories.IUserRepository;
import com.example.apismisservicios.negocios.services.IUserService;
import com.example.apismisservicios.security.models.entities.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UserIpml implements IUserService {

    IUserRepository userRepository;

    public UserIpml(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Usuario getUserId(String id) {
        return userRepository.findById(Long.parseLong(id)).orElse(null);
    }
}
