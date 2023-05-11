package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.dto.AsientoEnviadoDto;
import com.ganaseguros.recursos.erp.entity.AsientosEnviadosEntity;
import com.ganaseguros.recursos.erp.entity.ColumnasReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAsientoEnviadoDao extends JpaRepository<AsientosEnviadosEntity,Long> {


    @Query("SELECT NEW com.ganaseguros.recursos.erp.dto.AsientoEnviadoDto ("
            + "a.asientoEnviadoId,"
            + "a.codReporte,"
            + "concat(a.codReporte ,' - ', a.nombreReporte),"
            + "a.codRamo,"
            + "concat(a.codRamo ,' - ', a.nombreRamo),"
            + "a.nroAsiento,"
            + "a.usuarioId,"
            + "a.fechaDesde,"
            + "a.fechaHasta,"
            + "a.fechaEnviado,"
            + "a.jsonEnviado,"
            + "a.jsonRecibido,"
            + "a.estadoAsientoId,"
            + "a.tipoReporteId,"
            + "concat(p.nombres ,' ', p.apellidoPaterno ,' ', p.apellidoMaterno),"
            + "d.descripcion,"
            + "dd.descripcion, "
            + "a.nombreArchivo ) "
            + "FROM AsientosEnviadosEntity a "
            + "INNER JOIN UsuarioEntity u ON u.usuarioId = a.usuarioId "
            + "INNER JOIN PersonaEntity p ON p.personaId = u.personaId "
            + "INNER JOIN DominioEntity d ON d.dominioId = a.estadoAsientoId "
            + "INNER JOIN DominioEntity dd ON dd.dominioId = a.tipoReporteId "
            + "WHERE a.estadoId=1000")
    public List<AsientoEnviadoDto> obtenerAsientoEnviadoTodos();
}
