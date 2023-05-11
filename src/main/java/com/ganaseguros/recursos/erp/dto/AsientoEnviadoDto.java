package com.ganaseguros.recursos.erp.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class AsientoEnviadoDto {
    private Long asientoEnviadoId;
    private Long codReporte;
    private String nombreReporte;
    private Long codRamo;
    private String nombreRamo;
    private String nroAsiento;
    private Long usuarioId;

    private Date fechaDesde;

    private Date fechaHasta;
    private Date fechaEnviado;
    private String jsonEnviado;
    private String jsonRecibido;
    private Long estadoAsientoId;
    private Long tipoReporteId;

    private String usuario;
    private String estadoAsiento;
    private String tipoReporte;
    private String nombreArchivo;

    public AsientoEnviadoDto(Long asientoEnviadoId, Long codReporte, String nombreReporte, Long codRamo, String nombreRamo, String nroAsiento, Long usuarioId, Date fechaDesde, Date fechaHasta, Date fechaEnviado, String jsonEnviado, String jsonRecibido, Long estadoAsientoId, Long tipoReporteId, String usuario, String estadoAsiento, String tipoReporte, String nombreArchivo) {
        this.asientoEnviadoId = asientoEnviadoId;
        this.codReporte = codReporte;
        this.nombreReporte = nombreReporte;
        this.codRamo = codRamo;
        this.nombreRamo = nombreRamo;
        this.nroAsiento = nroAsiento;
        this.usuarioId = usuarioId;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaEnviado = fechaEnviado;
        this.jsonEnviado = jsonEnviado;
        this.jsonRecibido = jsonRecibido;
        this.estadoAsientoId = estadoAsientoId;
        this.tipoReporteId = tipoReporteId;
        this.usuario = usuario;
        this.estadoAsiento = estadoAsiento;
        this.tipoReporte = tipoReporte;
        this.nombreArchivo = nombreArchivo;
    }

    public AsientoEnviadoDto() {
    }
}
