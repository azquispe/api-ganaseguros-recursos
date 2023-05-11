package com.ganaseguros.recursos.transversal.controller;

import com.ganaseguros.recursos.transversal.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import com.ganaseguros.recursos.utils.dto.ResponseDto;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/transversal")
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;


    @PostMapping("/v1/upload-file/{pNameFile}")
    public ResponseEntity<?> cargarArchivos(@RequestParam(required = true) MultipartFile file ,@PathVariable String pNameFile) {
        Map<String, Object> response = new HashMap<>();
        ResponseDto res = uploadFileService.uploadFile(file, pNameFile);
        response.put("codigoMensaje", res.getCodigo());
        response.put("mensaje", res.getMensaje());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
