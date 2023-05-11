package com.ganaseguros.recursos.usuarios.controller;


import com.ganaseguros.recursos.usuarios.dto.CredencialDto;
import com.ganaseguros.recursos.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import com.ganaseguros.recursos.utils.dto.ResponseDto;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody CredencialDto credencialDto) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = usuarioService.autentication(credencialDto.getLogin(), credencialDto.getPassword());
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000"))
            response.put("datosPersona", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
