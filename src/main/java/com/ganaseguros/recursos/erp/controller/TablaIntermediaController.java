package com.ganaseguros.recursos.erp.controller;

import com.ganaseguros.recursos.erp.dao.IViewReporteDao;
import com.ganaseguros.recursos.erp.dto.TablaIntermediaDto;
import com.ganaseguros.recursos.erp.service.TablaIntermediaService;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/erp")
public class TablaIntermediaController {

    @Autowired
    TablaIntermediaService tablaIntermediaService;

    @GetMapping("/v1/generate-intermediate-table")
    public ResponseEntity<?> generateIntermediateTable( ) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = tablaIntermediaService.generateIntermediateTable( );
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @GetMapping("/v1/get-intermediate-table")
    public ResponseEntity<?> getIntermediateTable( ) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = tablaIntermediaService.getIntermediateTable( );
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("tablaIntermedia", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PutMapping("/v1/actualizar-tabla-intermedia/{pTablaIntermediaId}")
    public ResponseEntity<?> actualizarTablaIntermedia(@PathVariable Long pTablaIntermediaId,  @RequestBody TablaIntermediaDto pTablaIntermediaDto){
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = tablaIntermediaService.actualizarTablaIntermedia(pTablaIntermediaId,  pTablaIntermediaDto );
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @GetMapping("/v1/obtener-reportes-todos")
    public ResponseEntity<?> obtenerReportesTodos( ) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = tablaIntermediaService.obtenerReporteTodos();
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("reporteTodos", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
