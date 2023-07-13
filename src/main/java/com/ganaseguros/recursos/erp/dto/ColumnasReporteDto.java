package com.ganaseguros.recursos.erp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ColumnasReporteDto {
    private Long columnaReporteId;
    private Long tipoReporteId;
    private String tipoReporte;
    private Long codReporte;
    private String nombreColumnaExcel;
    private String nombreReporte;
    private String tipoMoneda;
    private String tipo;
    private Long codConcepto;
    private Long colummaExcel;



    public ColumnasReporteDto(Long columnaReporteId, Long tipoReporteId,
                              String tipoReporte, Long codReporte,String nombreColumnaExcel,
                              String nombreReporte,String tipoMoneda, Long codConcepto, Long colummaExcel) {
        this.columnaReporteId = columnaReporteId;
        this.tipoReporteId = tipoReporteId;
        this.tipoReporte = tipoReporte;
        this.codReporte = codReporte;
        this.nombreColumnaExcel = nombreColumnaExcel;
        this.nombreReporte = nombreReporte;
        this.tipoMoneda = tipoMoneda;
        this.codConcepto = codConcepto;
        this.colummaExcel = colummaExcel;
    }

    public ColumnasReporteDto() {
    }
}
