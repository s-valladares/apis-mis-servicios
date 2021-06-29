package com.example.apismisservicios.security.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String persona_id;

    public static UserPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                .getRolNombre().name())).collect(Collectors.toList());

        return new UserPrincipal(usuario.getNombreUsuario(), usuario.getPassword(), authorities, usuario.getPersona().getId().toString());
    }

    public UserPrincipal(String email, String password, Collection<? extends GrantedAuthority> authorities, String persona_id) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.persona_id = persona_id;
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
        return email;
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

    public String getEmail() {
        return email;
    }

    public String getPersona(){return persona_id;}
}
