package com.ganaseguros.recursos.erp.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AsientoCabTemDto {
    private Long asientoCabTemId;
    private Long codReporte;
    private String tipoDocumento;
    private String numeroDocumento;
    private String complemento;
    private String extension;
    private String primerNombreRazonSocial;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String apellidoCasado;
    private String groupCode;
    private String vatGroup;
    private String debitorAccount;
    private String groupNum;
    private String numeroSiniestro;
    private String numeroPoliza;
    private String montoSiniestroPagar;
    private String codigoAutorizacion;
    private String nroFactura;
    private String siniestroId;
    private Long codRamo;
    private String taxDate;
    private String dueDate;
    private String referenceDate;
    private String nroasientoSap;
    private String jsonEnviadoSap;
    private String jsonRespuestaSap;
    private Long tipoReporteId;
    private String nombreArchivo;

    private String ramo;
}
