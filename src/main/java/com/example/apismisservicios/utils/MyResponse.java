package com.example.apismisservicios.utils;

import com.example.apismisservicios.utils.constantes.Const;
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

            response.put(Const.SUCCESS, false);
            response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
            response.put(Const.ERROR, errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public static ResponseEntity<?> emailRepetido(String email) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(CustomMessage.EXIST_EMAIL);

        if(email.equals(Const.GOOGLE)){
            errors.add(Const.GOOGLE);
        } else errors.add(Const.MANUAL);

        response.put(Const.SUCCESS, false);
        response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
        response.put(Const.ERROR, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> errorsDataBase(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        errors.add(ex.getMostSpecificCause().getMessage());
        response.put(Const.SUCCESS, false);
        response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
        response.put(Const.ERROR, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> errorsDataBaseInternal(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        errors.add(ex.getMostSpecificCause().getMessage());
        response.put(Const.SUCCESS, false);
        response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
        response.put(Const.ERROR, errors);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> errorsCredentials() {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(CustomMessage.CREDENTIALS_INCORRECT_MESSAGE);
        response.put(Const.SUCCESS, false);
        response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
        response.put(Const.ERROR, errors);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> errorNull(String id) {
        Map<String, Object> response = new HashMap<>();

        response.put(Const.SUCCESS, false);
        response.put(Const.MESSAGE, CustomMessage.FAIL_MESSAGE);
        response.put(Const.ERROR, "No existe el id: " + id);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static Map<String, Object> successAction(Object object){
        Map<String, Object> resp = new HashMap<>();
        resp.put(Const.SUCCESS, true);
        resp.put(Const.MESSAGE, CustomMessage.SUCCESS_MESSAGE);
        resp.put(Const.DATA, object);

        return resp;
    }

    public static Map<String, Object> successActionList(List<?> object){
        Map<String, Object> resp = new HashMap<>();
        resp.put(Const.SUCCESS, true);
        resp.put(Const.MESSAGE, CustomMessage.SUCCESS_MESSAGE);
        resp.put(Const.DATA, object);

        return resp;
    }
}
