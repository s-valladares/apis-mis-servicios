package com.example.apismisservicios.negocios.controllers;

import com.example.apismisservicios.negocios.dtos.ProfileDto;
import com.example.apismisservicios.negocios.services.IUserService;
import com.example.apismisservicios.security.jwt.JwtProvider;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(URLs.URL_API)
public class UserController {
    final UserService userService;
    final IUserService iUserService;
    final JwtProvider jwtProvider;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, IUserService _userService, JwtProvider jwtProvider){
        this.userService = userService;
        this.iUserService = _userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping(URLs.PROFILE)
    public ResponseEntity<?> profile( @RequestHeader(name="Authorization") String token){
        Map<String, Object> resp;
        Usuario user;
        ProfileDto profileDto;
        String userName = jwtProvider.getNombreUsuarioFromToken(token.replace("Bearer ", ""));

        try {
            Long id = userService.getByNombreUsuario(userName).get().getId();
            user = iUserService.getUserId(id);

            if(user == null){
                return MyResponse.errorNull(id);
            } else {
                profileDto = new ProfileDto(user.getNombreUsuario(), user.getPersona().getFirstName(), user.getPersona().getLastName(), user.getPersona().getPhone(), user.getPersona().getAddress(), user.getCreatedAt());
                profileDto.setId(id);
                resp = MyResponse.successAction(profileDto);
            }
        } catch (DataAccessException ex){
            return MyResponse.errorsDataBase(ex);
        }


        return  new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
