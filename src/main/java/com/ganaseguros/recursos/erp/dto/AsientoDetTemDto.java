package com.ganaseguros.recursos.erp.dto;

import lombok.Data;

@Data
public class AsientoDetTemDto {
    private Long asientoDetTemId;
    private Long asientoCabTemId;
    private Long codigoConcepto;
    private int columnaExcel;
    private String amountDebe;
    private String amountHaber;
    private String descripcionConcepto;
    private String descripcionCuenta;
    private String debeHaber;
}
