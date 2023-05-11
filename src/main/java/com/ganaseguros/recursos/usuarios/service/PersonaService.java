package com.ganaseguros.recursos.usuarios.service;

import com.ganaseguros.recursos.usuarios.dao.IPersonaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    @Autowired
    IPersonaDao iPersonaDao;


}
