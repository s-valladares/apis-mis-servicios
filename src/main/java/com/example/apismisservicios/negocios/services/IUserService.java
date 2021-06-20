package com.example.apismisservicios.negocios.services;

import com.example.apismisservicios.security.models.entities.Usuario;

public interface IUserService {
    Usuario getUserId(String id);
}
