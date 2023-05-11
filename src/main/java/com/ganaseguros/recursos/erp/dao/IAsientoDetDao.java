package com.ganaseguros.recursos.erp.dao;

import com.ganaseguros.recursos.erp.entity.AsientoDetTemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAsientoDetDao extends JpaRepository<AsientoDetTemEntity,Long> {
    @Query("select a from AsientoDetTemEntity a where a.estadoId = 1000 ")
    public List<AsientoDetTemEntity> findByAsientoCabId();

    @Query("select a from AsientoDetTemEntity a where a.estadoId = 1000 and a.asientoDetTemId = :pAsientoDetTemId ")
    public Optional<AsientoDetTemEntity> findById(Long pAsientoDetTemId);

    @Query(value = "select ad.asiento_det_tem_id, ad.asiento_cab_tem_id ,ad.codigo_concepto ,ad.columna_excel,\n" +
            "  CASE\n" +
            "    WHEN ti.debe_haber='D' THEN CAST( ad.amount AS text )\n" +
            "    ELSE ''\n" +
            "  END as amount_debe,\n" +
            "    CASE\n" +
            "    WHEN ti.debe_haber='H' THEN CAST( ad.amount AS text )\n" +
            "    ELSE ''\n" +
            "  END as amount_haber\n" +
            ",ti.descripcion_concepto , ti.descripcion_cuenta  ,  ti.debe_haber  from erp.asiento_det_tem ad \n" +
            "left join erp.asiento_cab_tem ac on ac.asiento_cab_tem_id  = ad.asiento_cab_tem_id  \n" +
            "left join erp.tabla_intermedia ti on ti.cod_reporte = ac.cod_reporte  and ti.cod_ramo = ac.cod_ramo  and ti.codigo_concepto = lpad(ad.codigo_concepto, 5, '0')  \n" +
            "where ad.estado_id = 1000 and ad.asiento_cab_tem_id  IN  (:pAsientosCabId)", nativeQuery = true)
    List<Object[]> findAsientosDetTemByAsientosCabId(List<Long> pAsientosCabId);

    /*@Query(value = "select ad.asiento_det_tem_id, ad.asiento_cab_tem_id ,ad.codigo_concepto ,\n" +
            "  CASE\n" +
            "    WHEN ti.debe_haber='D' THEN CAST( ad.amount AS text )\n" +
            "    ELSE ''\n" +
            "  END as amount_debe,\n" +
            "    CASE\n" +
            "    WHEN ti.debe_haber='H' THEN CAST( ad.amount AS text )\n" +
            "    ELSE ''\n" +
            "  END as amount_haber\n" +
            ",ti.descripcion_concepto , ti.descripcion_cuenta  ,  ti.debe_haber  from erp.asiento_det_tem ad \n" +
            "left join erp.asiento_cab_tem ac on ac.asiento_cab_tem_id  = ad.asiento_cab_tem_id  \n" +
            "left join erp.tabla_intermedia ti on ti.cod_reporte = ac.cod_reporte  and ti.cod_ramo = ac.cod_ramo  and ti.codigo_concepto = lpad(ad.codigo_concepto, 5, '0')  \n" +
            "where ad.estado_id = 1000 and ad.asiento_cab_tem_id  = :pAsientoCabId", nativeQuery = true)
    List<Object[]> findAsientosDetTemByAsientoCabId(Long pAsientoCabId);*/
}
