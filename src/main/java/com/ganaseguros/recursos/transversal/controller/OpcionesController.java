package com.ganaseguros.recursos.transversal.controller;


import com.ganaseguros.recursos.transversal.service.OpcionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import com.ganaseguros.recursos.utils.dto.ResponseDto;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/transversal")
public class OpcionesController {

    @Autowired
    OpcionesService opcionesService;

    @GetMapping("/v1/generate-menu/{pUsuarioId}")
    public ResponseEntity<?> generateMenu(@PathVariable("pUsuarioId") Long pUsuarioId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = opcionesService.generateMenu(pUsuarioId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000"))
            response.put("opciones", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
