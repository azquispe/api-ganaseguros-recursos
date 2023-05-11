package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.entity.AsientoCabTemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAsientoCabDao extends JpaRepository<AsientoCabTemEntity,Long> {

    @Query(value = "select distinct(ac.cod_reporte) as codReporte, ti2.nombre_reporte as nombreReporte,ti2.tipo_moneda ,dom.dominio_id , dom.descripcion\n" +
            "from erp.asiento_cab_tem ac \n" +
            "inner join (select ti.cod_reporte, ti.nombre_reporte,ti.tipo_moneda  from erp.tabla_intermedia ti  where ti.estado_id  = 1000 group by ti.cod_reporte, ti.nombre_reporte,ti.tipo_moneda  order by 1 asc) ti2 on ac.cod_reporte = ti2.cod_reporte\n" +
            "inner join transversal.dominios dom on dom.dominio_id = ac.tipo_reporte_id \n" +
            "where ac.estado_id = 1000 and ac.tipo_reporte_id = :pTipoReporteId and ac.usuario_registro_id = :pUsuarioId order by 1 asc ", nativeQuery = true)
    List<Object[]> findReportesTemByUsuarioId(Long pUsuarioId, Long pTipoReporteId);

    @Query(value = "select distinct(ac.cod_reporte) as codReporte, ti2.nombre_reporte as nombreReporte ,ti2.tipo_moneda,dom.dominio_id , dom.descripcion\n" +
            "from erp.asiento_cab_tem ac \n" +
            "inner join (select ti.cod_reporte, ti.nombre_reporte,ti.tipo_moneda  from erp.tabla_intermedia ti  where ti.estado_id  = 1000 group by ti.cod_reporte, ti.nombre_reporte,ti.tipo_moneda  order by 1 asc) ti2 on ac.cod_reporte = ti2.cod_reporte\n" +
            "inner join transversal.dominios dom on dom.dominio_id = ac.tipo_reporte_id \n" +
            "where ac.estado_id = 1000 and ac.usuario_registro_id = :pUsuarioId order by 1 asc ", nativeQuery = true)
    List<Object[]> findReportesTemByUsuarioId(Long pUsuarioId);

    @Query(value = "select ac.asiento_cab_tem_id , ac.cod_reporte,ac.cod_ramo , ti2.ramo ,\n" +
            "ac.tax_date, ac.due_date, ac.reference_date,ac.nroasiento_sap,ac.json_enviado_sap,ac.json_respuesta_sap\n" +
            "from erp.asiento_cab_tem ac \n" +
            "inner join \n" +
            "(select ti.cod_reporte, ti.nombre_reporte,ti.cod_ramo,ti.ramo  from erp.tabla_intermedia ti \n" +
            "where ti.estado_id  = 1000\n" +
            "group by ti.cod_reporte, ti.nombre_reporte ,ti.cod_ramo,ti.ramo order by 1 asc) ti2\n" +
            "on ac.cod_reporte = ti2.cod_reporte and ac.cod_ramo  = ti2.cod_ramo\n" +
            "where ac.usuario_registro_id = 1000 and ac.estado_id = 1000 and ac.cod_reporte = :pReporteId  order by 3 asc", nativeQuery = true)
    List<Object[]> findAsientoCabTemByReporteId(Long pReporteId);



    @Query("select a from AsientoCabTemEntity a where a.estadoId = 1000 and a.asientoCabTemId = :pAsientoCabId ")
    Optional<AsientoCabTemEntity> findById(Long pAsientoCabId);


    @Query(value = "select * from erp.fn_obtener_cantidad_asientos(:pUsuarioId,:pTipoReporteId)", nativeQuery = true)
    List<Object[]> obtenerCantidadAsientos(Long pUsuarioId, Long pTipoReporteId);







}
