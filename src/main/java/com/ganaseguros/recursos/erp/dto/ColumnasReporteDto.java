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
    private String codReporteCore;
    private String nombreReporte;
    private String tipoMoneda;
    private String tipo;
    private Long codConcepto;
    private Long colummaExcel;
    private String colummaCore;


    public ColumnasReporteDto(Long columnaReporteId, Long tipoReporteId,
                              String tipoReporte, Long codReporte,String codReporteCore,
                              String nombreReporte,String tipoMoneda, Long codConcepto, Long colummaExcel,String colummaCore) {
        this.columnaReporteId = columnaReporteId;
        this.tipoReporteId = tipoReporteId;
        this.tipoReporte = tipoReporte;
        this.codReporte = codReporte;
        this.codReporteCore = codReporteCore;
        this.nombreReporte = nombreReporte;
        this.tipoMoneda = tipoMoneda;
        this.codConcepto = codConcepto;
        this.colummaExcel = colummaExcel;
        this.colummaCore = colummaCore;
    }

    public ColumnasReporteDto() {
    }
}
