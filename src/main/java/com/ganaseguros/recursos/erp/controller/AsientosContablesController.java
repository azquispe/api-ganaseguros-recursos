package com.ganaseguros.recursos.erp.controller;

import com.ganaseguros.recursos.erp.dto.AsientoDetTemDto;
import com.ganaseguros.recursos.erp.dto.RequestAsientoDto;
import com.ganaseguros.recursos.erp.service.AsientosContablesService;
import com.ganaseguros.recursos.transversal.service.UploadFileService;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/erp")
public class AsientosContablesController {

    @Autowired
    AsientosContablesService asientosContablesService;

    @Autowired
    UploadFileService uploadFileService;
    @GetMapping("/v1/obtener-reportes/{pUsuarioId}/{pTipoReporteId}")
    public ResponseEntity<?> getReportesByUsuarioId(@PathVariable Long pUsuarioId ,@PathVariable(required = false) Long pTipoReporteId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.getReportesByUsuarioIdAndTipoReporteId(pUsuarioId,pTipoReporteId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("reportes", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @GetMapping("/v1/obtener-asiento-cab/{pReporteId}")
    public ResponseEntity<?> obtenerAsientoCabPorReporteId(@PathVariable Long pReporteId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.obtenerAsientoCabPorReporteId(pReporteId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("ramos", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @GetMapping("/v1/obtener-asientos-det/{pAsientoCabId}")
    public ResponseEntity<?> getAsientosByAsientoCabId(@PathVariable Long pAsientoCabId) {
        Map<String, Object> response = new HashMap<>();
        List<Long> lstAsientoCab = new ArrayList<>();
        lstAsientoCab.add(pAsientoCabId);
        ResponseDto res = asientosContablesService.getAsientosDetByAsientosCabId(lstAsientoCab);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000")){
            response.put("asientos", res.getElementoGenerico());
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/v1/cargar-archivo-asiento/{pTipoReporteId}/{pUsuarioId}")
    public ResponseEntity<?> cargarArchivoAsiento(@RequestParam(required = true) MultipartFile file , @PathVariable Long pTipoReporteId,@PathVariable Long pUsuarioId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.cargarArchivoAsiento(file,pTipoReporteId, pUsuarioId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PostMapping("/v1/enviar-erp-asientos/{pTipoReporteId}")
    public ResponseEntity<?> enviarErpAsientos( @PathVariable Long pTipoReporteId,@RequestBody RequestAsientoDto requestAsientoDto ) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto token = asientosContablesService.obtenerToken();
        ResponseDto res = asientosContablesService.enviarErpAsientoContable(pTipoReporteId,token,requestAsientoDto);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1"))
            response.put("respuestaErp", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    /*@PostMapping("/v1/enviar-erp-asientos-todos/{pUsuarioId}/{pTipoReporteId}")
    public ResponseEntity<?> enviarErpAsientosTodos( @PathVariable Long pUsuarioId,@PathVariable Long pTipoReporteId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.enviarErpAsientoContableTodos(pUsuarioId, pTipoReporteId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1"))
            response.put("respuestaErp", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }*/
    @GetMapping("/v1/obtener-asientos-enviados-todos")
    public ResponseEntity<?> obtenerAsientosEnviadosTodos() {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.obtenerAsientosEnviadosTodos();
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000"))
            response.put("asientosEnviados", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PostMapping("/v1/modificar-asiento-detalle/{asientoDetTemId}")
    public ResponseEntity<?> modificarAsientoDetalle( @RequestBody AsientoDetTemDto asientoDetTemDto, @PathVariable Long asientoDetTemId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.modificarAsientoDetalle(asientoDetTemDto,asientoDetTemId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/v1/obtener-cantidad-asientos/{pUsuarioId}/{pTipoReporteId}")
    public ResponseEntity<?> obtenerCantidadAsientos( @PathVariable Long pUsuarioId,@PathVariable Long pTipoReporteId) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = asientosContablesService.obtenerCantidadAsientos(pUsuarioId,pTipoReporteId);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        if(res.getCodigo().equals("1000"))
            response.put("cantidad", res.getElementoGenerico());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


}
