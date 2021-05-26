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

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.Set;

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
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("Campos mal puestos o email inválido", HttpStatus.BAD_REQUEST);
        }
        if(userService.existsByEmail(newUserDto.getEmail())){
            return new ResponseEntity<>("Ya existe este email registrado", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(newUserDto.getNombreUsuario(), newUserDto.getEmail(), passwordEncoder.encode(newUserDto.getPassword()), true);

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

        if(newUserDto.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());

        usuario.setRoles(roles);

        userService.save(usuario);

        return new ResponseEntity<>("Usuario guardado correctamente", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getNombreUsuario(), loginUserDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return  new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}
