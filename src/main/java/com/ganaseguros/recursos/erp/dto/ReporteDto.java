package com.ganaseguros.recursos.erp.dto;

import lombok.Data;

@Data
public class ReporteDto {
    private Long codReporte;
    private String nombreReporte;
    private String tipoMoneda;
    private Long tipoReporteId;
    private String tipoReporte;

    public ReporteDto(Long codReporte, String nombreReporte, String tipoMoneda, Long tipoReporteId, String tipoReporte) {
        this.codReporte = codReporte;
        this.nombreReporte = nombreReporte;
        this.tipoMoneda = tipoMoneda;
        this.tipoReporteId = tipoReporteId;
        this.tipoReporte = tipoReporte;
    }

    public ReporteDto() {
    }
}
