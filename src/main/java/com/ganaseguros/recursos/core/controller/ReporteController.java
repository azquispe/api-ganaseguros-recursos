package com.ganaseguros.recursos.core.controller;

import com.ganaseguros.recursos.core.service.ReportesService;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/core")
public class ReporteController {

    @Autowired
    ReportesService reportesService;

    @GetMapping("/v1/obtener-reportes/{pUsuarioId}/{pTipoReporteId}/{pFechaInicio}/{pFechaFin}")
    public ResponseEntity<?> getReportesByUsuarioId(@PathVariable Long pUsuarioId ,
                                                    @PathVariable Long pTipoReporteId,
                                                    @PathVariable String pFechaInicio,
                                                    @PathVariable String pFechaFin) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = reportesService.obtenerReporteCore(pUsuarioId,pTipoReporteId,pFechaInicio,pFechaFin);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("reportes", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
