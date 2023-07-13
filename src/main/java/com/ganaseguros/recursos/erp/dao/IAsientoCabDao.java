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

    @Query(value = "select \n" +
            "ac.asiento_cab_tem_id , \n" +
            "ac.cod_reporte,\n" +
            "ac.cod_ramo , \n" +
            "ti2.ramo ,\n" +
            "ac.tipo_documento, --saocio negocio desde aqui\n" +
            "ac.numero_documento,\t\n" +
            "ac.complemento,\t\n" +
            "ac.extension,\t\n" +
            "ac.primer_nombre_razon_social,\t\n" +
            "ac.segundo_nombre,\t\n" +
            "ac.apellido_paterno,\n" +
            "ac.apellido_materno,\t\n" +
            "ac.apellido_casado,\n" +
            "ac.group_code,\n" +
            "ac.vat_group,\n" +
            "ac.debitor_account,\t\n" +
            "ac.group_num,\t\n" +
            "ac.numero_siniestro,\n" +
            "ac.numero_poliza,\n" +
            "ac.monto_siniestro_pagar,\n" +
            "ac.codigo_autorizacion,\n" +
            "ac.nro_factura,\n" +
            "ac.siniestro_id,\n" +
            "ac.tax_date, \n" +
            "ac.due_date, \n" +
            "ac.reference_date,\n" +
            "ac.nroasiento_sap,\n" +
            "ac.json_enviado_sap,\n" +
            "ac.json_respuesta_sap, -- hasta aqui \n" +
            "ac.tipo_reporte_id, \n"+
            "ac.nombre_archivo \n"+
            "from erp.asiento_cab_tem ac \n" +
            "inner join \n" +
            "(select ti.cod_reporte, ti.nombre_reporte,ti.cod_ramo,ti.ramo  from erp.tabla_intermedia ti where ti.estado_id  = 1000  group by ti.cod_reporte, ti.nombre_reporte ,ti.cod_ramo,ti.ramo order by 1 asc) ti2\n" +
            "on ac.cod_reporte = ti2.cod_reporte and ac.cod_ramo  = ti2.cod_ramo\n" +
            "where ac.usuario_registro_id = 1000 and ac.estado_id = 1000 and ac.cod_reporte = :pReporteId  order by 3 asc", nativeQuery = true)
    List<Object[]> findAsientoCabTemByReporteId(Long pReporteId);



    @Query("select a from AsientoCabTemEntity a where a.estadoId = 1000 and a.asientoCabTemId = :pAsientoCabId ")
    Optional<AsientoCabTemEntity> findById(Long pAsientoCabId);


    @Query(value = "select * from erp.fn_obtener_cantidad_asientos(:pUsuarioId,:pTipoReporteId)", nativeQuery = true)
    List<Object[]> obtenerCantidadAsientos(Long pUsuarioId, Long pTipoReporteId);







}
