package com.example.apismisservicios.security.controllers;
import com.example.apismisservicios.negocios.models.entities.Persona;
import com.example.apismisservicios.negocios.services.IPersonService;
import com.example.apismisservicios.security.dtos.JwtDto;
import com.example.apismisservicios.security.dtos.LoginUserDto;
import com.example.apismisservicios.security.dtos.NewUserDto;
import com.example.apismisservicios.enums.RolNombre;
import com.example.apismisservicios.security.dtos.RegisterDto;
import com.example.apismisservicios.security.jwt.JwtProvider;
import com.example.apismisservicios.security.models.entities.Rol;
import com.example.apismisservicios.security.models.entities.Usuario;
import com.example.apismisservicios.security.services.RolService;
import com.example.apismisservicios.security.services.UserService;
import com.example.apismisservicios.utils.MyResponse;
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
import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final UserService userService;
    final RolService rolService;
    final JwtProvider jwtProvider;
    final IPersonService personService;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService, RolService rolService, JwtProvider jwtProvider, IPersonService personService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.rolService = rolService;
        this.jwtProvider = jwtProvider;
        this.personService = personService;
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){



        if(userService.existsByNombreUsuario(newUserDto.getNombreUsuario())){
            String type = userService.getByNombreUsuario(newUserDto.getNombreUsuario()).get().getAuth();
            return MyResponse.emailRepetido(type);
        }



        if(MyResponse.errorsFields(bindingResult) != null){
            return MyResponse.errorsFields(bindingResult);
        }

        Map<String, Object> res;
        Usuario usuario = new Usuario(newUserDto.getNombreUsuario(), passwordEncoder.encode(newUserDto.getPassword()), true, newUserDto.getAuth());
        Set<Rol> roles = new HashSet<>();

        Persona persona = personService.getId(Long.parseLong(newUserDto.getPersona_id()));

        usuario.setPersona(persona);
        try{
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

            if(newUserDto.getRoles().contains(RolNombre.ROLE_ADMIN.toString()))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            usuario.setRoles(roles);
            Usuario newUser = userService.save(usuario);
            RegisterDto registerDto = new RegisterDto(newUser.getId(), newUser.getNombreUsuario(), persona.getNombres(), persona.getApellidos(), persona.getTelefono(), persona.getDireccion());
            res = MyResponse.successAction(registerDto);
        } catch (DataAccessException ex){
            return MyResponse.errorsDataBase(ex);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){

        if(MyResponse.errorsFields(bindingResult) != null){
            return MyResponse.errorsFields(bindingResult);
        }

        Map<String, Object> resp;
        JwtDto jwtDto;

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
            resp = MyResponse.successAction(jwtDto);
        } catch (DataAccessException ex){
            return MyResponse.errorsDataBase(ex);
        }catch (Exception exe){
            return MyResponse.errorsCredentials();
        }

        return  new ResponseEntity<>(resp, HttpStatus.OK);
    }


}
