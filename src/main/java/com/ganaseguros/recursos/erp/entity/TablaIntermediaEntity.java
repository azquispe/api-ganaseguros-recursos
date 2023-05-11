package com.ganaseguros.recursos.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tabla_intermedia", schema = "erp")
public class TablaIntermediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tabla_intermedia_id")
    private Long tablaIntermediaId;

    @Column(name = "cod_ramo")
    private Long codRamo;

    @Column(name = "ramo")
    private String ramo;

    @Column(name = "cod_reporte")
    private Long codReporte;

    @Column(name = "campo_reporte")
    private String campoReporte;

    @Column(name = "nombre_reporte")
    private String nombreReporte;

    @Column(name = "tipo_moneda")
    private String tipoMoneda;

    @Column(name = "codigo_concepto")
    private String codigoConcepto;

    @Column(name = "descripcion_concepto")
    private String descripcionConcepto;

    @Column(name = "codigo_cuenta_sap_puc")
    private String codigoCuentaSapPuc;

    @Column(name = "descripcion_cuenta")
    private String descripcionCuenta;

    @Column(name = "debe_haber")
    private String debeHaber;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "estado_id")
    private Long estadoId;

}
