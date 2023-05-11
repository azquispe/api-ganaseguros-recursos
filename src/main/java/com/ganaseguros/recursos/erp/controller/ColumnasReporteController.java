package com.ganaseguros.recursos.erp.controller;

import com.ganaseguros.recursos.erp.dto.ColumnasReporteDto;
import com.ganaseguros.recursos.erp.service.ColumnasReporteService;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/erp")
public class ColumnasReporteController {

    @Autowired
    ColumnasReporteService columnasReporteService;

    @GetMapping("/v1/obtener-columnas-reportes")
    public ResponseEntity<?> obtenerColumnasReportes( ) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = columnasReporteService.obtenerColumnasReportesTodos( );
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("columnasExcel", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PutMapping("/v1/actualizar-columnas-reportes/{columnaReporteId}")
    public ResponseEntity<?> actualizarColumnasReportes( @PathVariable Long columnaReporteId,@RequestBody ColumnasReporteDto columnasReporteDto) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = columnasReporteService.actualizarColumnasReportes(columnaReporteId ,columnasReporteDto);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @DeleteMapping("/v1/anular-columna-reporte/{columnaReporteId}")
    public ResponseEntity<?> anularColumnaReporte( @PathVariable Long columnaReporteId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = columnasReporteService.anularColumnaReporteById(columnaReporteId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
