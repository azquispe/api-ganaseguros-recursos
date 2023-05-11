package com.ganaseguros.recursos.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "asientos_enviados", schema = "erp")
public class AsientosEnviadosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asiento_enviado_id")
    private Long asientoEnviadoId;

    @Column(name = "cod_reporte")
    private Long codReporte;

    @Column(name = "nombre_reporte")
    private String nombreReporte;

    @Column(name = "cod_ramo")
    private Long codRamo;

    @Column(name = "nombre_ramo")
    private String nombreRamo;

    @Column(name = "nro_asiento")
    private String nroAsiento;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "fecha_desde")
    private Date fechaDesde;

    @Column(name = "fecha_hasta")
    private Date fechaHasta;

    @Column(name = "fecha_enviado")
    private Date fechaEnviado;

    @Column(name = "json_enviado")
    private String jsonEnviado;

    @Column(name = "json_recibido")
    private String jsonRecibido;

    @Column(name = "estado_asiento_id")
    private Long estadoAsientoId;

    @Column(name = "tipo_reporte_id")
    private Long tipoReporteId;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "estado_id")
    private Long estadoId;

}
