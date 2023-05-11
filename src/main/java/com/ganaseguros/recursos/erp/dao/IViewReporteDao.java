package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.dto.ColumnasReporteDto;
import com.ganaseguros.recursos.erp.dto.ViewReporteDto;
import com.ganaseguros.recursos.erp.entity.TablaIntermediaEntity;
import com.ganaseguros.recursos.erp.entity.ViewReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IViewReporteDao extends JpaRepository<ViewReporteEntity,Long> {
    @Query("SELECT NEW com.ganaseguros.recursos.erp.dto.ViewReporteDto ("
            + "a.reporteId,"
            + "a.nombreReporte,"
            + "a.tipoMoneda)"
            + "FROM ViewReporteEntity a ")
    public List<ViewReporteDto> obtenerReportesTodos();

}
