package com.example.apismisservicios.security.controllers;
import com.example.apismisservicios.security.dtos.JwtDto;
import com.example.apismisservicios.security.dtos.LoginUserDto;
import com.example.apismisservicios.security.dtos.NewUserDto;
import com.example.apismisservicios.security.enums.RolNombre;
import com.example.apismisservicios.security.jwt.JwtProvider;
import com.example.apismisservicios.security.models.entities.Rol;
import com.example.apismisservicios.security.models.entities.Usuario;
import com.example.apismisservicios.security.services.RolService;
import com.example.apismisservicios.security.services.UserService;

import com.example.apismisservicios.utils.ErrorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final UserService userService;
    final RolService rolService;
    final JwtProvider jwtProvider;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService, RolService rolService, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.rolService = rolService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){

        if(ErrorsResponse.controlErrorsFields(bindingResult) != null){
            return ErrorsResponse.controlErrorsFields(bindingResult);
        }

        Map<String, Object> res = new HashMap<>();
        Usuario usuario = new Usuario(newUserDto.getNombreUsuario(), newUserDto.getEmail(), passwordEncoder.encode(newUserDto.getPassword()), true);
        Set<Rol> roles = new HashSet<>();
        try{
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

            if(newUserDto.getRoles().contains(RolNombre.ROLE_ADMIN.toString()))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            Usuario newUser = userService.save(usuario);
            newUser.setPassword("");
            usuario.setRoles(roles);
            res.put("response", newUser);
        } catch (DataAccessException ex){
            return ErrorsResponse.controlErrorsDataBase(ex);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){

        if(ErrorsResponse.controlErrorsFields(bindingResult) != null){
            return ErrorsResponse.controlErrorsFields(bindingResult);
        }

        JwtDto jwtDto;

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getNombreUsuario(), loginUserDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        } catch (DataAccessException ex){
            return ErrorsResponse.controlErrorsDataBase(ex);
        }

        return  new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}
