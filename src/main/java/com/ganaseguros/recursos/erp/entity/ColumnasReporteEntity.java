package com.ganaseguros.recursos.erp.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "columnas_reporte", schema = "erp")
public class ColumnasReporteEntity {
    @Id
    @Column(name = "columna_reporte_id")
    private Long columnaReporteId;

    @Column(name = "tipo_reporte_id")
    private Long tipoReporteId;

    @Column(name = "cod_reporte")
    private Long codReporte;

    @Column(name = "cod_reporte_core")
    private String codReporteCore;

    @Column(name = "cod_concepto")
    private Long codConcepto;

    @Column(name = "columma_excel")
    private Long colummaExcel;

    @Column(name = "columma_core")
    private String colummaCore;

    @Column(name = "estado_id")
    private Long estadoId;

}
