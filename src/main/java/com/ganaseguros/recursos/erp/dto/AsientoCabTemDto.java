package com.ganaseguros.recursos.erp.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AsientoCabTemDto {
    private Long asientoCabTemId;
    private Long codReporte;
    private Long codRamo;
    private String ramo;
    private String taxDate;
    private String dueDate;
    private String referenceDate;
    private String nroasientoSap;
    private String jsonEnviadoSap;
    private String jsonRespuestaSap;
    private String nombreArchivo;
}
