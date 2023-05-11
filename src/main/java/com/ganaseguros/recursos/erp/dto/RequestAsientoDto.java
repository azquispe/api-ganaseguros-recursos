package com.ganaseguros.recursos.erp.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class RequestAsientoDto {
    private Map<String, Object> asiento;
    private Long asientoCabTemId;
    private Long usuarioId;
    private Date fechaDesde;
    private Date fechaHasta;
}
