package com.ganaseguros.recursos.transversal.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.ganaseguros.recursos.utils.dto.ResponseDto;

@Service
@RequiredArgsConstructor
public class UploadFileService {




    @Value("${dir.archivo.contables}")
    private String  dirArchivoContables;

    public ResponseDto uploadFile (MultipartFile pFile, String pNameFile){
        String path = dirArchivoContables+"/"+pNameFile+".xlsx";
        ResponseDto res = new ResponseDto();
        try{
            if(pFile==null || pNameFile == null){
                res.setCodigo("1001");
                res.setMensaje("Archivo y Nombre de Archivo son requeridos");
                return res;
            }
            byte[] bytes = pFile.getBytes();
            Path pathFile = Paths.get(path);
            Files.write(pathFile, bytes);

            res.setCodigo("1000");
            res.setMensaje("EXITO");

        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("ERROR");
        }
        return res;
    }
}
