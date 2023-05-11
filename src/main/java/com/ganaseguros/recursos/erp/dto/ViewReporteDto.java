package com.ganaseguros.recursos.erp.dto;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class ViewReporteDto {
    private Long reporteId;

    private String nombreReporte;

    private String tipoMoneda;

    public ViewReporteDto(Long reporteId, String nombreReporte, String tipoMoneda) {
        this.reporteId = reporteId;
        this.nombreReporte = nombreReporte;
        this.tipoMoneda = tipoMoneda;
    }

    public ViewReporteDto() {
    }
}
