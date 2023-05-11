package com.ganaseguros.recursos.transversal.service;

import com.ganaseguros.recursos.transversal.dao.IOpcionesDao;
import com.ganaseguros.recursos.transversal.entity.OpcionesEntity;
import com.ganaseguros.recursos.utils.funciones.FuncionesGenerales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ganaseguros.recursos.utils.dto.ResponseDto;

import java.util.*;

@Service
public class OpcionesService {

    @Autowired
    IOpcionesDao iOpcionesDao;

    public ResponseDto generateMenu (Long pUsuarioId){
        ResponseDto res = new ResponseDto();
        try{
            List<Long> lstTitulo = new ArrayList<>();
            List<Long> lstSubTitulo = new ArrayList<>();
            List<Long> lstEnlace = new ArrayList<>();
            List<OpcionesEntity> lstOpcion = iOpcionesDao.findOpcionesByUsuarioId(pUsuarioId);
            if(lstOpcion.isEmpty()){
                res.setCodigo("1001");
                res.setMensaje("No existe opciones registrados");
                return res;
            }

            for (OpcionesEntity op :lstOpcion) {
                if(op.getTipoOpcionId()==1032){ // ENLACE
                    lstEnlace.add(op.getOpcionId());
                    int intento = 1;
                    while (intento<=2){
                        intento++;
                        Optional<OpcionesEntity> opp = iOpcionesDao.findOpcionesByOpcionId(op.getOpcionOrigenId());
                        if(opp.isEmpty()) continue;
                        if(!opp.isEmpty() &&  opp.get().getTipoOpcionId() == 1030)   lstTitulo.add(opp.get().getOpcionId());
                        if(!opp.isEmpty() && opp.get().getTipoOpcionId() == 1031)   lstSubTitulo.add(opp.get().getOpcionId());
                        op = opp.get();

                    }
                }
                else if (op.getTipoOpcionId() == 1031) // SUBTITULO
                {
                    lstSubTitulo.add(op.getOpcionId());
                    Optional<OpcionesEntity> opp = iOpcionesDao.findOpcionesByOpcionId(op.getOpcionOrigenId());
                    if(!opp.isEmpty() &&  opp.get().getTipoOpcionId() == 1030)   lstTitulo.add(opp.get().getOpcionId());
                    List<OpcionesEntity> oppp = iOpcionesDao.findOpcionesByOpcionOrigenId(op.getOpcionId());
                    for (OpcionesEntity obj:oppp) {
                        lstEnlace.add(obj.getOpcionId());
                    }

                }else{
                    lstTitulo.add(op.getOpcionId());
                    List<OpcionesEntity> opp = iOpcionesDao.findOpcionesByOpcionOrigenId(op.getOpcionId());
                    for (OpcionesEntity obj:opp) {
                        List<OpcionesEntity> oppp = iOpcionesDao.findOpcionesByOpcionOrigenId(obj.getOpcionId());
                        for (OpcionesEntity objj:oppp) {
                            lstEnlace.add(objj.getOpcionId());
                        }
                        lstSubTitulo.add(obj.getOpcionId());
                    }
                }
            }

            lstTitulo = FuncionesGenerales.eliminarDuplicadoList(lstTitulo);
            lstSubTitulo = FuncionesGenerales.eliminarDuplicadoList(lstSubTitulo);
            lstEnlace = FuncionesGenerales.eliminarDuplicadoList(lstEnlace);

            List<Map<String, Object>> lstMenuNivel1 = new ArrayList<>();
            for (Long pOpcionTitulo:lstTitulo) {

                Optional<OpcionesEntity> objTitulo = iOpcionesDao.findOpcionesByOpcionId(pOpcionTitulo);
                Map<String, Object> objMenuNivel1 = new HashMap<>();
                objMenuNivel1.put("opcionId",objTitulo.get().getOpcionId());
                objMenuNivel1.put("nombre",objTitulo.get().getNombre());
                objMenuNivel1.put("tipoOpcionId",objTitulo.get().getTipoOpcionId());


                List<OpcionesEntity>  lstSub =  iOpcionesDao.findOpcionesByOpcionOrigenId(pOpcionTitulo);
                List<Map<String, Object>> lstMenuNivel2 = null;
                List<Map<String, Object>> lstMenuNivel3 = null;
                for (OpcionesEntity objSub :lstSub) {
                    lstMenuNivel2 = new ArrayList<>();
                    lstMenuNivel3 = new ArrayList<>();
                    if( lstSubTitulo.contains(objSub.getOpcionId())) {
                        Map<String, Object> objMenuNivel2 = new HashMap<>();
                        objMenuNivel2.put("opcionId",objSub.getOpcionId());
                        objMenuNivel2.put("nombre",objSub.getNombre());
                        objMenuNivel2.put("tipoOpcionId",objSub.getTipoOpcionId());
                        List<OpcionesEntity> enlace =  iOpcionesDao.findOpcionesByOpcionOrigenId(objSub.getOpcionId());
                        for (OpcionesEntity obj :enlace) {
                            if( lstEnlace.contains(obj.getOpcionId())) {
                                Map<String, Object> objMenuNivel3 = new HashMap<>();
                                objMenuNivel3.put("opcionId",obj.getOpcionId());
                                objMenuNivel3.put("nombre",obj.getNombre());
                                objMenuNivel3.put("tipoOpcionId",obj.getTipoOpcionId());
                                objMenuNivel3.put("link",obj.getLink());
                                lstMenuNivel3.add(objMenuNivel3);
                            }
                        }
                        objMenuNivel2.put("menu",lstMenuNivel3);
                        lstMenuNivel2.add(objMenuNivel2);
                    }
                    if( lstEnlace.contains(objSub.getOpcionId())) {
                        Map<String, Object> objMenuNivel3 = new HashMap<>();
                        objMenuNivel3.put("opcionId",objSub.getOpcionId());
                        objMenuNivel3.put("nombre",objSub.getNombre());
                        objMenuNivel3.put("tipoOpcionId",objSub.getTipoOpcionId());
                        objMenuNivel3.put("link",objSub.getLink());
                        lstMenuNivel3.add(objMenuNivel3);
                    }
                }

                if(!lstMenuNivel2.isEmpty() ){
                    objMenuNivel1.put("menu",lstMenuNivel2);
                }else{
                    objMenuNivel1.put("menu",lstMenuNivel3);
                }
                lstMenuNivel1.add(objMenuNivel1);
            }


            res.setCodigo("1000");
            res.setMensaje("Opciones encontrados");
            res.setElementoGenerico(lstMenuNivel1);
            return res;
        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }

}
