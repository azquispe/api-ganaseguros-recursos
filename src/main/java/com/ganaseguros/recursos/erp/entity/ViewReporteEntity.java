package com.ganaseguros.recursos.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "view_reportes_ti", schema = "erp")
public class ViewReporteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reporte_id")
    private Long reporteId;

    @Column(name = "nombre_reporte")
    private String nombreReporte;

    @Column(name = "tipo_moneda")
    private String tipoMoneda;
}
