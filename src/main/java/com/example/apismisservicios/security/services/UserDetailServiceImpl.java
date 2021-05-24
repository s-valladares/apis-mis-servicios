package com.example.apismisservicios.security.services;

import com.example.apismisservicios.security.models.entities.Usuario;
import com.example.apismisservicios.security.models.entities.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    private final UserService userService;

    @Autowired
    public UserDetailServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario user = userService.getByNombreUsuario(nombreUsuario).get();
        return UsuarioPrincipal.build(user);
    }
}
