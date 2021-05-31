package com.example.apismisservicios.negocios.controllers;

import com.example.apismisservicios.negocios.models.entities.Persona;
import com.example.apismisservicios.negocios.services.IPersonService;
import com.example.apismisservicios.utils.CustomMessage;
import com.example.apismisservicios.utils.MyResponse;
import com.example.apismisservicios.utils.constantes.Const;
import com.example.apismisservicios.utils.constantes.URLs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(URLs.URL_API)
public class PersonController {

    private final String service = URLs.PERSONAS;
    private final IPersonService personService;
    @Autowired
    public PersonController(IPersonService personService){
        this.personService = personService;
    }

    Map<String, Object> response = new HashMap<>();

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(service)
    public ResponseEntity<?> index() {
        response.clear();
        List<Persona> objNew;
        try {
            objNew = personService.getAll();
        } catch (DataAccessException ex) {
            return MyResponse.errorsDataBaseInternal(ex);
        }
        response = MyResponse.successActionList(objNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(URLs.PERSONAS)
    public ResponseEntity<?> create(@Valid @RequestBody Persona x, BindingResult result) {

        Persona objNew;

        if(MyResponse.errorsFields(result) != null){
            return MyResponse.errorsFields(result);
        }

        try {
            objNew = personService.create(x);

        } catch (DataAccessException ex) {
            response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
            response.put(Const.ERROR, ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(Const.MESSAGE, CustomMessage.SUCCESS_MESSAGE);
        response.put(Const.SUCCESS, true);
        response.put(Const.DATA, objNew.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
