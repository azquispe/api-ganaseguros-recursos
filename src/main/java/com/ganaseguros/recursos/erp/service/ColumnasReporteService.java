package com.ganaseguros.recursos.erp.service;

import com.ganaseguros.recursos.erp.dao.IColumnasReporteDao;
import com.ganaseguros.recursos.erp.dto.ColumnasReporteDto;
import com.ganaseguros.recursos.erp.entity.ColumnasReporteEntity;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
import org.hibernate.usertype.LoggableUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColumnasReporteService {

    @Autowired
    IColumnasReporteDao iColumnasReporteDao;

    public ResponseDto obtenerColumnasReportesTodos(){
        ResponseDto res = new ResponseDto();
        try{
            List<ColumnasReporteDto> lst =  iColumnasReporteDao.obtenerColumnasReporte();
            res.setCodigo("1000");
            res.setMensaje("Datos encontrados");
            res.setElementoGenerico(lst);

            return res;
        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("error: "+ex.toString());
            return res;
        }
    }
    public ResponseDto actualizarColumnasReportes(Long columnaReporteId,ColumnasReporteDto columnasReporteDto){
        ResponseDto res = new ResponseDto();
        try{
            ColumnasReporteEntity update  =  iColumnasReporteDao.findById(columnaReporteId).get();
            update.setTipoReporteId(columnasReporteDto.getTipoReporteId());
            update.setCodReporte(columnasReporteDto.getCodReporte());
            update.setCodConcepto(columnasReporteDto.getCodConcepto());
            update.setColummaExcel(columnasReporteDto.getColummaExcel());
            iColumnasReporteDao.save(update);
            res.setCodigo("1000");
            res.setMensaje("Datos encontrados");
            return res;
        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("error: "+ex.toString());
            return res;
        }
    }
    public ResponseDto anularColumnaReporteById(Long columnaReporteId){
        ResponseDto res = new ResponseDto();
        try{
            ColumnasReporteEntity update  =  iColumnasReporteDao.findById(columnaReporteId).get();
            update.setEstadoId(1001L);
            iColumnasReporteDao.save(update);
            res.setCodigo("1000");
            res.setMensaje("Anulación Exitosa");
            return res;

        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje("Error al anular Columna Reporte");
            return res;
        }
    }



}
