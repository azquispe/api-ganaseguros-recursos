package com.ganaseguros.recursos.transversal.controller;

import com.ganaseguros.recursos.transversal.service.DominioService;
import com.ganaseguros.recursos.transversal.service.OpcionesService;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/transversal")
public class DominiosController {

    @Autowired
    DominioService dominioService;


    @GetMapping(path = "/v1/findByDominio/{pDominio}")
    public ResponseEntity<?> findByDominio(@PathVariable("pDominio") String pDominio) {

        Map<String, Object> response = new HashMap<>();
        ResponseDto res = dominioService.obtenerDominioPorDominio(pDominio);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000"))
            response.put("dominios", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

}
