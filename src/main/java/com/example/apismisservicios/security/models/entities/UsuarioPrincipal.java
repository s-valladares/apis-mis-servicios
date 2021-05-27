package com.example.apismisservicios.security.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.apismisservicios.negocios.models.entities.Persona;
import com.example.apismisservicios.utils.AuditModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails{
    private final String nombreUsuario;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Persona persona;

    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                .getRolNombre().name())).collect(Collectors.toList());

        return new UsuarioPrincipal(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), authorities, usuario.getPersona());
    }

    public UsuarioPrincipal(String nombreUsuario, String email, String password, Collection<? extends GrantedAuthority> authorities, Persona persona) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.persona = persona;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public Persona getPersona(){return persona;}
}
