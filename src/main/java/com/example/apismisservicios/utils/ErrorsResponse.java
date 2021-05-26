package com.example.apismisservicios.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorsResponse {
    public static ResponseEntity<?> controlErrorsFields(BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public static ResponseEntity<?> controlErrorsDataBase(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error al insertar en la base de datos");
        response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
