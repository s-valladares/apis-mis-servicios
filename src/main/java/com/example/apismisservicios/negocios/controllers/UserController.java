package com.example.apismisservicios.negocios.controllers;

import com.example.apismisservicios.negocios.dtos.ProfileDto;
import com.example.apismisservicios.negocios.services.IUserService;
import com.example.apismisservicios.security.jwt.JwtTokenFilter;
import com.example.apismisservicios.security.models.entities.Usuario;
import com.example.apismisservicios.security.services.UserService;
import com.example.apismisservicios.utils.MyResponse;
import com.example.apismisservicios.utils.constantes.URLs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/misservicios/api")
public class UserController {
    private final String service = URLs.USERS;
    final UserService userService;
    final IUserService iUserService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, IUserService _userService){
        this.userService = userService;
        this.iUserService = _userService;
    }

    @GetMapping("users/me")
    public ResponseEntity<?> profile(){
        Map<String, Object> resp;
        Usuario user;
        ProfileDto profileDto;

        try {
            user = iUserService.getUserId("64");

            if(user == null){
                return MyResponse.errorNull("4");
            } else {
                profileDto = new ProfileDto(user.getNombreUsuario(), user.getPersona().getNombres(), user.getPersona().getApellidos(), user.getPersona().getTelefono(), user.getPersona().getDireccion(), user.getCreatedAt());
                resp = MyResponse.successAction(profileDto);
            }
        } catch (DataAccessException ex){
            return MyResponse.errorsDataBase(ex);
        }


        return  new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
