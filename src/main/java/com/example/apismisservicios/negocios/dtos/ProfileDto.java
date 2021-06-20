package com.example.apismisservicios.negocios.dtos;

import java.util.Date;

public class ProfileDto {
    private Long id;
    private String nameUser;
    private String firstName;
    private String lastName;
    private String celPhone;
    private String addresses;
    private Date createdAt;

    public ProfileDto(String nameUser, String firstName, String lastName, String celPhone, String addresses, Date createdAt) {
        this.nameUser = nameUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.celPhone = celPhone;
        this.addresses = addresses;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
