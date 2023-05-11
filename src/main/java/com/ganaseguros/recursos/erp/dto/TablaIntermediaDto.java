package com.ganaseguros.recursos.erp.dto;


import lombok.Data;

@Data
public class TablaIntermediaDto {
    private Long tablaIntermediaId;
    private Long codRamo;
    private String ramo;
    private Long codReporte;
    private String campoReporte;
    private String nombreReporte;
    private String tipoMoneda;
    private String codigoConcepto;
    private String descripcionConcepto;
    private String codigoCuentaSapPuc;
    private String descripcionCuenta;
    private String debeHaber;
    private Long usuarioId;

}
