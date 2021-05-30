package com.example.apismisservicios.negocios.models.entities;

import com.example.apismisservicios.utils.AuditModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacío")
    @Column(length = 50)
    private String nombres;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacío")
    @Column(length = 50)
    private String apellidos;

    @Column(length = 100)
    private String direccion;

    @Column(length = 8)
    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private static final long serialVersionUID = 1L;
}
