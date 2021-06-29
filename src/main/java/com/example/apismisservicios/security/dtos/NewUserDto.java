package com.example.apismisservicios.security.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class NewUserDto {

    @NotBlank(message = "El usuario no puede ser vacío")
    @Email(message = "El nombre de usuario debe ser un email")
    private String email;
    @NotBlank(message = "La contraseña no puede ser vacía")
    private String password;
    private Set<String> roles = new HashSet<>();
    @NotBlank(message = "La persona no puede ser vacía")
    private String persona_id;
    @NotBlank(message = "Debe ingresar el tipo de autenticación")
    private String auth;
    private Boolean enabled;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setPersona_id(String persona_id) {
        this.persona_id = persona_id;
    }
}
