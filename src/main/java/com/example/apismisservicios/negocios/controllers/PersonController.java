package com.example.apismisservicios.negocios.controllers;

import com.example.apismisservicios.negocios.models.entities.Persona;
import com.example.apismisservicios.negocios.services.IPersonService;
import com.example.apismisservicios.utils.URLs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/misservicios/api")
public class PersonController {

    private final String service = URLs.PERSONAS;
    private final IPersonService personService;
    private final static Logger logger = LoggerFactory.getLogger(PersonController.class);
    @Autowired
    public PersonController(IPersonService personService){
        this.personService = personService;
    }

    Map<String, Object> response = new HashMap<>();

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/personas")
    public ResponseEntity<?> index() {
        List<Persona> objNew;
        try {
            objNew = personService.getAll();
        } catch (DataAccessException ex) {
            response.put("mensaje", "Error al obtener de la base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("size", objNew.size());
        response.put("rows", objNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(URLs.PERSONAS)
    public ResponseEntity<?> create(@Valid @RequestBody Persona x, BindingResult result) {

        Persona objNew = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            objNew = personService.create(x);

        } catch (DataAccessException ex) {
            response.put("mensaje", "Error al insertar en la base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "OK");
        response.put("RES", objNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
