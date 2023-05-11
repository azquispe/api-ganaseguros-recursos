package com.ganaseguros.recursos.transversal.service;

import com.ganaseguros.recursos.transversal.dao.IDominioDao;
import com.ganaseguros.recursos.transversal.dto.DominioDto;
import com.ganaseguros.recursos.transversal.entity.DominioEntity;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DominioService {

    @Autowired
    IDominioDao iDominioDao;

    public ResponseDto obtenerDominioPorDominio(String pDominio) {
        ResponseDto res = new ResponseDto();
        try{
            DominioDto dominioDto = null;
            List<DominioDto> lstDominioDto = new ArrayList<>();
            List<DominioEntity> lstDominioEntity =  this.iDominioDao.findByDominio(pDominio);
            for (DominioEntity dom :lstDominioEntity) {
                dominioDto = new DominioDto();
                dominioDto.setDominioId(dom.getDominioId());
                dominioDto.setDominio(dom.getDominio());
                dominioDto.setAbreviatura(dom.getAbreviatura());
                dominioDto.setDescripcion(dom.getDescripcion());
                lstDominioDto.add(dominioDto);
            }
            res.setCodigo("1000");
            res.setMensaje("Dominios encontrados");
            res.setElementoGenerico(lstDominioDto);
        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("error: "+ex.toString());
        }
        return res;

    }
}
