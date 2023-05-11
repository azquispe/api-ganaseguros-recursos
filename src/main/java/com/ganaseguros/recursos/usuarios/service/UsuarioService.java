package com.ganaseguros.recursos.usuarios.service;

import com.ganaseguros.recursos.usuarios.dao.IPersonaDao;
import com.ganaseguros.recursos.usuarios.dao.IUsuarioDao;
import com.ganaseguros.recursos.usuarios.dto.PersonaDto;
import com.ganaseguros.recursos.usuarios.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.ganaseguros.recursos.utils.dto.ResponseDto;
@Service
public class UsuarioService {
    @Autowired
    IUsuarioDao iUsuarioDao;

    @Autowired
    IPersonaDao iPersonaDao;

    public ResponseDto autentication (String pLogin, String pPassword){
        ResponseDto res = new ResponseDto();
        try{
            Optional<UsuarioEntity> us = iUsuarioDao.findByLogin(pLogin);
            if(!us.isPresent() || !us.get().getPassword().equals(pPassword)){
                res.setCodigo("1001");
                res.setMensaje("Usuario no Existe");
                return res;
            }
            List<PersonaDto> pe = iPersonaDao.findPersonaByUduarioId(us.get().getUsuarioId());
            if(pe.isEmpty()){
                res.setCodigo("1001");
                res.setMensaje("Usuario no Existe");
                return res;
            }
            res.setCodigo("1000");
            res.setMensaje("Acceso Correcto");
            res.setElementoGenerico(pe.get(0));
            return res;

        }catch (Exception ex){
            res.setCodigo("1001");
            res.setMensaje(ex.toString());
            return res;
        }
    }
}
