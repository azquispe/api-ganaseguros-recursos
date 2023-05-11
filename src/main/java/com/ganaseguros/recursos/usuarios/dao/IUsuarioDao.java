package com.ganaseguros.recursos.usuarios.dao;

import com.ganaseguros.recursos.usuarios.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUsuarioDao extends JpaRepository<UsuarioEntity,Long> {

    @Query("select a from UsuarioEntity a where a.estadoId = 1000 and a.login = :pLogin ")
    public Optional<UsuarioEntity> findByLogin(String pLogin);



}
