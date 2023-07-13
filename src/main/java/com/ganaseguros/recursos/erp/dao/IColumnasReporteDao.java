package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.dto.AsientoEnviadoDto;
import com.ganaseguros.recursos.erp.dto.ColumnasReporteDto;
import com.ganaseguros.recursos.erp.entity.AsientoDetTemEntity;
import com.ganaseguros.recursos.erp.entity.ColumnasReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IColumnasReporteDao extends JpaRepository<ColumnasReporteEntity, Long> {
    /*@Query("select a from ColumnasReporteEntity a where a.estadoId = 1000 ")
    public List<ColumnasReporteEntity> findAll();*/

    @Query("SELECT NEW com.ganaseguros.recursos.erp.dto.ColumnasReporteDto ("
            + "a.columnaReporteId,"
            + "a.tipoReporteId,"
            + "d.descripcion,"
            + "a.codReporte,"
            + "a.nombreColumnaExcel,"
            + "concat(a.codReporte ,' - ', r.nombreReporte),"
            + "r.tipoMoneda,"
            + "a.codConcepto,"
            + "a.colummaExcel)"

            + "FROM ColumnasReporteEntity a "
            + "left JOIN ViewReporteEntity r ON r.reporteId = a.codReporte "
            + "left JOIN DominioEntity d ON d.dominioId = a.tipoReporteId "
            + "WHERE a.estadoId=1000")
    public List<ColumnasReporteDto> obtenerColumnasReporte();

}
