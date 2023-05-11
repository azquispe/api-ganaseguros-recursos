package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.entity.ViewRamoEntity;
import com.ganaseguros.recursos.erp.entity.ViewReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IViewRamoDao extends JpaRepository<ViewRamoEntity,Long> {
}
