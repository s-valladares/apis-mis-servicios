package com.example.apismisservicios.negocios.models.entities;

import com.example.apismisservicios.utils.AuditModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Persona extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacío")
    @Column(length = 50)
    private String firstName;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacío")
    @Column(length = 50)
    private String lastName;

    @Column(length = 100)
    private String address;

    @Column(length = 8)
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String nombres) {
        this.firstName = nombres;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String apellidos) {
        this.lastName = apellidos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String direccion) {
        this.address = direccion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String telefono) {
        this.phone = telefono;
    }

    private static final long serialVersionUID = 1L;
}
