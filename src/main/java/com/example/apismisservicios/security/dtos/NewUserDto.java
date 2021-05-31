package com.example.apismisservicios.security.dtos;

import com.example.apismisservicios.utils.AuditModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class NewUserDto {
    @NotBlank(message = "El usuario no puede ser vacío")
    private String nombreUsuario;
    @NotBlank(message = "La contraseña no puede ser vacía")
    private String password;
    private Set<String> roles = new HashSet<>();
    @NotBlank(message = "La persona no puede ser vacía")
    private String persona_id;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(String persona_id) {
        this.persona_id = persona_id;
    }
}
