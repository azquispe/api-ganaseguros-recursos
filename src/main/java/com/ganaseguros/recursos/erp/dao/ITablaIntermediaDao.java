package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.entity.TablaIntermediaEntity;
import com.ganaseguros.recursos.usuarios.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITablaIntermediaDao extends JpaRepository< TablaIntermediaEntity,Long> {
    @Query("select a from TablaIntermediaEntity a where a.estadoId = 1000 and a.codReporte = :pCodReporte ")
    public List<TablaIntermediaEntity> findByCodigoReporte(Long pCodReporte);

    @Query("select a from TablaIntermediaEntity a where a.estadoId = 1000 ")
    public List<TablaIntermediaEntity> findAll();
}
