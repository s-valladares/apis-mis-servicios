package com.example.apismisservicios.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyResponse {
    public static ResponseEntity<?> errorsFields(BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors().stream().map(err -> {
                return "campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("success", false);
            response.put("message", CustomMessage.FAIL_MESSAGE);
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public static ResponseEntity<?> errorsDataBase(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        errors.add(ex.getMostSpecificCause().getMessage());
        response.put("success", false);
        response.put("message", CustomMessage.FAIL_MESSAGE);
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> errorsCredentials() {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(CustomMessage.CREDENTIALS_INCORRECT_MESSAGE);
        response.put("success", false);
        response.put("message", CustomMessage.FAIL_MESSAGE);
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static Map<String, Object> successAction(Object object){
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("message", CustomMessage.SUCCESS_MESSAGE);
        resp.put("res", object);

        return resp;
    }
}
