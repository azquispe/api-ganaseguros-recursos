package com.ganaseguros.recursos.erp.dto;

import lombok.Data;

@Data
public class SocioNegocioDto {
    private String codigoProveedor;
    private String numeroDocumento;
    private String tipoDocumento;
    private String extension;
    private String groupCode;
    private String vatGroup;
    private String debitorAccount;
    private String groupNum;
    private String nombreTomador;
    private String nombreAsegurado;
    private String nombreBeneficiario;
    private String nombreIntermedio;
    private String numeroSiniestro;
    private String numeroPoliza;
    private String fechaInicio;
    private String fechaFin;
    private String fechaSiniestro;
    private String valorAsegurado;
    private String montoTotalSiniestro;
    private String montoPagarCompania;
    private String montoReembolsoReaseguro;
    private String siniestroId;
}
